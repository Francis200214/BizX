package com.biz.operation.log;

import com.biz.operation.log.manager.OperationLogManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 操作日志切面类。
 *
 * <p>该类使用AOP技术，在标注了{@link OperationLog}注解的方法或类上，拦截并记录操作日志。</p>
 *
 * <p>该类提供了日志内容的动态解析、日志记录以及异常处理等功能。</p>
 *
 * <p>它还使用了{@link ThreadLocal}来处理操作方法中的线程安全问题。</p>
 *
 * @author francis
 * @since 2024-05-31 16:27
 **/
@Aspect
@Slf4j
public class OperationLogAspect {


    private final OperationLogManager operationLogManager;

    /**
     * 注解缓存，用于减少反射调用的频率。
     */
    private final Map<Method, OperationLog> annotationCache = new ConcurrentHashMap<>();


    /**
     * 构造函数，注入必要地依赖。
     *
     * @param operationLogManager 本地用户存储服务
     * @throws BeansException 如果在注入依赖时出现错误
     */
    public OperationLogAspect(OperationLogManager operationLogManager) throws BeansException {
        this.operationLogManager = operationLogManager;
    }

    /**
     * 在目标方法执行之前执行的操作。
     *
     * <p>该方法会解析并存储日志内容，以便在方法执行后进行记录。</p>
     *
     * @param joinPoint 方法的连接点信息
     * @throws Throwable 如果在解析日志内容时出现错误
     */
    @Before("@annotation(com.biz.operation.log.OperationLog)")
    public void logMethod(JoinPoint joinPoint) throws Throwable {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            OperationLog operationLog = this.getNowOperationLog(joinPoint);
            if (operationLog == null) {
                return;
            }

            operationLogManager.handlerParameter(signature, joinPoint.getArgs(), operationLog);
        } catch (Exception e) {
            log.error("OperationLogAspect error", e);
            // 清空当前线程变量信息
            operationLogManager.flushThreadLocal();
        }
    }

    /**
     * 在目标方法正常执行完成后的操作。
     *
     * <p>该方法会在方法成功执行后发送日志信息。</p>
     *
     * @param joinPoint 方法的连接点信息
     */
    @AfterReturning(pointcut = "@annotation(com.biz.operation.log.OperationLog)")
    public void afterReturningMethod(JoinPoint joinPoint) {
        OperationLog operationLog = this.getNowOperationLog(joinPoint);
        if (operationLog == null) {
            return;
        }

        // 发送日志
        operationLogManager.push();
    }

    /**
     * 在目标方法抛出异常后的操作。
     *
     * <p>该方法会在方法执行异常时发送日志信息，并包含异常详情。</p>
     *
     * @param joinPoint 方法的连接点信息
     * @param e         方法抛出的异常
     */
    @AfterThrowing(pointcut = "@annotation(com.biz.operation.log.OperationLog)", throwing = "e")
    public void afterThrowingMethod(JoinPoint joinPoint, Throwable e) {
        OperationLog operationLog = this.getNowOperationLog(joinPoint);
        if (operationLog == null) {
            return;
        }

        // 发送日志
        operationLogManager.push(e);
    }

    /**
     * 在目标方法执行完成后的最终操作。
     *
     * <p>无论方法是正常返回还是抛出异常，都会清理ThreadLocal中的数据。</p>
     *
     * @param joinPoint 方法的连接点信息
     */
    @After("@annotation(com.biz.operation.log.OperationLog)")
    public void afterMethod(JoinPoint joinPoint) {
        operationLogManager.flushThreadLocal();
    }


    /**
     * 获取当前方法上的 {@link OperationLog} 注解信息。
     *
     * @param joinPoint 方法的连接点信息
     * @return 如果方法上存在 {@link OperationLog} 注解，则返回注解信息；否则返回null
     */
    private OperationLog getNowOperationLog(JoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            return annotationCache.computeIfAbsent(method, m -> method.getAnnotation(OperationLog.class));

        } catch (Throwable e) {
            operationLogManager.flushThreadLocal();
            log.error("在OperationLogAspect中获取OperationLog注解时发生异常", e);
        }

        return null;
    }
}
