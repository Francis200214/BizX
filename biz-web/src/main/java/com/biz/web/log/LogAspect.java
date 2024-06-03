package com.biz.web.log;

import com.biz.common.utils.Common;
import com.biz.web.log.recorder.LogRecorder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 自定义日志切面
 *
 * @author francis
 * @create 2024-05-31 16:27
 **/
@Aspect
@Component
@Slf4j
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

    @Autowired
    private LogRecorder logRecorder;


    /**
     * 进入日志目标方法之前操作
     *
     * @param joinPoint 方法信息
     * @throws Throwable
     */
    @Before("@annotation(Loggable)")
    public void logMethod(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Loggable loggable = this.getNowLoggable(joinPoint);
        if (loggable == null) {
            return;
        }

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();

        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }

        String operatorId = parser.parseExpression(loggable.operatorId()).getValue(context, String.class);
        String operatorName = parser.parseExpression(loggable.operatorName()).getValue(context, String.class);

        String content = loggable.content()
                .replace("{userId}", operatorId)
                .replace("{now()}", Common.now())
                .replace("{logLargeType}", loggable.logLargeType())
                .replace("{logSmallType}", loggable.logSmallType());

        content = parser.parseExpression(content).getValue(context, String.class);

        operatorIdHolder.set(operatorId);
        operatorNameHolder.set(operatorName);
        contentHolder.set(content);

    }


    /**
     * 执行完目标方法后的操作
     *
     * @param joinPoint 方法信息
     */
    @AfterReturning(pointcut = "@annotation(Loggable)")
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
    @AfterThrowing(pointcut = "@annotation(Loggable)", throwing = "e")
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
    @After("@annotation(Loggable)")
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
