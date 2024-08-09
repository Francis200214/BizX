package com.biz.web.log;

import com.biz.common.spel.SpELUtils;
import com.biz.common.utils.Common;
import com.biz.web.log.recorder.LogRecorder;
import com.biz.web.log.store.LocalUserStoreService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * 自定义日志切面
 *
 * @author francis
 * @since 2024-05-31 16:27
 **/
@Aspect
@Slf4j
@ConditionalOnProperty(prefix = "biz.log", name = "enable", havingValue = "true", matchIfMissing = false)
public class LogAspect implements ApplicationContextAware {

    /**
     * 当前操作方法参数日志内容
     */
    private static final ThreadLocal<String> contentHolder = new ThreadLocal<>();

    /**
     * 日志记录
     */
    private LogRecorder logRecorder;

    /**
     * 当前线程的用户
     */
    private LocalUserStoreService localUserStoreService;

    /**
     * 追踪ID
     */
//    private TraceStoreService traceStoreService;


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
            ExpressionParser parser = new SpelExpressionParser();
            String operatorName = SpELUtils.parseExpression(
                    "'" + localUserStoreService.getOperationUserName() + "'", context, parser, String.class);

            String content = loggable.content()
                    .replace("operationName", operatorName)
                    .replace("now()", Common.now())
                    .replace("logLargeType", loggable.logLargeType())
                    .replace("logSmallType", loggable.logSmallType());

            content = parseSpelExpressions(content, context, parser);

            contentHolder.set(content);

        } catch (Exception e) {
            log.error("TraceLogAspect error", e);
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
        logRecorder.record(null, loggable, localUserStoreService.getOperationUserId(), localUserStoreService.getOperationUserName(), contentHolder.get(), e);
    }

    /**
     * 清空当前线程变量
     */
    private void flushThreadLocal() {
        localUserStoreService.removeAll();
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


    /**
     * 解析 SpEL 表达式
     *
     * @param content 内容
     * @param context 上下文
     * @param parser  解析器
     * @return
     */
    private static String parseSpelExpressions(String content, StandardEvaluationContext context, ExpressionParser parser) {
        StringBuilder parsedContent = new StringBuilder();
        int start = 0;
        while (start < content.length()) {
            int openIndex = content.indexOf("#{", start);
            if (openIndex == -1) {
                parsedContent.append(content.substring(start));
                break;
            }
            parsedContent.append(content, start, openIndex);
            int closeIndex = content.indexOf("}", openIndex);
            if (closeIndex == -1) {
                parsedContent.append(content.substring(openIndex));
                break;
            }
            String expression = content.substring(openIndex + 2, closeIndex);
            String value = SpELUtils.parseExpression(expression, context, parser, String.class);
            parsedContent.append(value);
            start = closeIndex + 1;
        }
        return parsedContent.toString();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            this.localUserStoreService = applicationContext.getBean(LocalUserStoreService.class);
            this.logRecorder = applicationContext.getBean(LogRecorder.class);
        } catch (Exception e) {
            log.error("Not found LocalUserStoreService Bean Or TraceStoreService Bean in TraceLogAspect");
        }
    }

}
