package com.biz.operation.log;

import com.biz.operation.log.processor.OperationLogProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.BeansException;

/**
 * {@code OperationLogAspect}是一个操作日志切面类。
 *
 * <p>该类使用AOP技术，在标注了{@link OperationLog}注解的方法或类上，拦截并记录操作日志。</p>
 *
 * <p>该类提供了日志内容的动态解析、日志记录以及异常处理等功能。</p>
 *
 * <p>它还使用了{@link ThreadLocal}来处理操作方法中的线程安全问题。</p>
 *
 * <p>该类包含三个主要的AOP方法：</p>
 * <ul>
 *     <li>{@link #logMethod(JoinPoint)}：在方法执行前触发，进行日志处理的前置操作。</li>
 *     <li>{@link #afterReturningMethod(JoinPoint)}：在方法正常返回后触发，记录日志。</li>
 *     <li>{@link #afterThrowingMethod(JoinPoint, Throwable)}：在方法抛出异常时触发，记录异常信息。</li>
 * </ul>
 *
 * @see OperationLog
 * @see OperationLogProcessor
 * @see ThreadLocal
 * @author francis
 * @since 1.0.0
 * @version 1.0.0
 **/
@Aspect
@Slf4j
public class OperationLogAspect {

    /**
     * 操作日志处理器，用于处理日志的生成与记录。
     */
    private final OperationLogProcessor operationLogProcessor;

    /**
     * 构造函数，初始化{@code OperationLogProcessor}。
     *
     * @param operationLogProcessor 操作日志处理器，用于处理日志的生成与记录
     * @throws BeansException 如果OperationLogProcessor Bean无法注入，将抛出该异常
     */
    public OperationLogAspect(OperationLogProcessor operationLogProcessor) throws BeansException {
        this.operationLogProcessor = operationLogProcessor;
    }

    /**
     * 在标注了{@link OperationLog}注解的方法执行前触发。
     *
     * <p>该方法调用{@link OperationLogProcessor#processBeforeMethod(JoinPoint)}进行日志处理。</p>
     *
     * @param joinPoint 连接点，提供了关于被拦截方法的相关信息
     * @see JoinPoint
     */
    @Before("@annotation(com.biz.operation.log.OperationLog)")
    public void logMethod(JoinPoint joinPoint) {
        operationLogProcessor.processBeforeMethod(joinPoint);
    }

    /**
     * 在标注了{@link OperationLog}注解的方法正常返回后触发。
     *
     * <p>该方法调用{@link OperationLogProcessor#processAfterReturning(JoinPoint)}记录日志。</p>
     *
     * @param joinPoint 连接点，提供了关于被拦截方法的相关信息
     * @see JoinPoint
     */
    @AfterReturning(pointcut = "@annotation(com.biz.operation.log.OperationLog)")
    public void afterReturningMethod(JoinPoint joinPoint) {
        operationLogProcessor.processAfterReturning(joinPoint);
    }

    /**
     * 在标注了{@link OperationLog}注解的方法抛出异常时触发。
     *
     * <p>该方法调用{@link OperationLogProcessor#processAfterThrowing(JoinPoint, Throwable)}记录异常信息。</p>
     *
     * @param joinPoint 连接点，提供了关于被拦截方法的相关信息
     * @param e 抛出的异常
     * @see JoinPoint
     * @see Throwable
     */
    @AfterThrowing(pointcut = "@annotation(com.biz.operation.log.OperationLog)", throwing = "e")
    public void afterThrowingMethod(JoinPoint joinPoint, Throwable e) {
        operationLogProcessor.processAfterThrowing(joinPoint, e);
    }

}
