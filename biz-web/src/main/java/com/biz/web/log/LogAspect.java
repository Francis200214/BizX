package com.biz.web.log;

import com.biz.common.spel.SpELUtils;
import com.biz.common.utils.Common;
import com.biz.web.log.recorder.LogRecorder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * 自定义日志切面
 *
 * @author francis
 * @create 2024-05-31 16:27
 **/
@Aspect
@Slf4j
@ConditionalOnProperty(prefix = "biz.log", name = "enable", havingValue = "true", matchIfMissing = false)
public class LogAspect {

    /**
     * 当前操作人id
     */
    private static final ThreadLocal<String> operatorIdHolder = new ThreadLocal<>();

    /**
     * 当前操作人姓名
     */
    private static final ThreadLocal<String> operatorNameHolder = new ThreadLocal<>();

    /**
     * 当前操作方法参数日志内容
     */
    private static final ThreadLocal<String> contentHolder = new ThreadLocal<>();


    private LogRecorder logRecorder;

    public LogAspect() {
        try {
            logRecorder = new LogRecorder();
        } catch (Exception e) {
            log.error("Not found LogRecorder Bean in LogAspect");
        }
    }

    /**
     * 进入日志目标方法之前操作
     *
     * @param joinPoint 方法信息
     * @throws Throwable
     */
    @Before("@annotation(com.biz.web.log.Loggable)")
    public void logMethod(JoinPoint joinPoint) throws Throwable {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Loggable loggable = this.getNowLoggable(joinPoint);
            if (loggable == null) {
                return;
            }

            StandardEvaluationContext context = SpELUtils.createContext(signature.getParameterNames(), joinPoint.getArgs());

            String operatorId = SpELUtils.parseExpression(loggable.operatorId(), context, String.class);
            String operatorName = SpELUtils.parseExpression(loggable.operatorName(), context, String.class);


            String content = loggable.content()
                    .replace("{userId}", operatorId)
                    .replace("{now()}", Common.now())
                    .replace("{logLargeType}", loggable.logLargeType())
                    .replace("{logSmallType}", loggable.logSmallType());

            content = SpELUtils.parseExpression(content, context, String.class);

            operatorIdHolder.set(operatorId);
            operatorNameHolder.set(operatorName);
            contentHolder.set(content);
        } catch (Exception e) {
            log.error("LogAspect error", e);
            // 清空当前线程变量信息
            this.flushThreadLocal();
        }
    }


    /**
     * 执行完目标方法后的操作
     *
     * @param joinPoint 方法信息
     */
    @AfterReturning(pointcut = "@annotation(com.biz.web.log.Loggable)")
    public void afterReturningMethod(JoinPoint joinPoint) {
        Loggable loggable = this.getNowLoggable(joinPoint);
        if (loggable == null) {
            return;
        }

        // 发送日志
        this.pushLog(loggable, null);
        // 清空当前线程变量信息
        this.flushThreadLocal();
    }


    /**
     * 执行完目标方法时出现了异常信息执行的操作
     *
     * @param joinPoint 方法信息
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "@annotation(com.biz.web.log.Loggable)", throwing = "e")
    public void afterThrowingMethod(JoinPoint joinPoint, Throwable e) {
        Loggable loggable = this.getNowLoggable(joinPoint);
        if (loggable == null) {
            return;
        }

        // 发送日志
        this.pushLog(loggable, e);
        // 清空当前线程变量信息
        this.flushThreadLocal();
    }


    /**
     * 最终通知，确保无论方法是否成功执行或抛出异常，都会清理 ThreadLocal
     *
     * @param joinPoint 方法信息
     */
    @After("@annotation(com.biz.web.log.Loggable)")
    public void afterMethod(JoinPoint joinPoint) {
        this.flushThreadLocal();
    }


    /**
     * 发出日志信息
     *
     * @param loggable 日志类型
     * @param e        异常信息
     */
    private void pushLog(Loggable loggable, Throwable e) {
        logRecorder.record(loggable, operatorIdHolder.get(), operatorNameHolder.get(), contentHolder.get(), e);
    }

    /**
     * 清空当前线程变量
     */
    private void flushThreadLocal() {
        operatorIdHolder.remove();
        operatorNameHolder.remove();
        contentHolder.remove();
    }


    /**
     * 获取方法信息中的 Loggable 注解信息
     *
     * @param joinPoint 方法信息
     * @return
     */
    private Loggable getNowLoggable(JoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            return method.getAnnotation(Loggable.class);

        } catch (Throwable e) {
            // 清空当前线程数据
            this.flushThreadLocal();
            log.error("操作日志切面中操作 getNowLoggable 时出现了异常", e);
        }

        return null;
    }


}
