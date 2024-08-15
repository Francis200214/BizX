package com.biz.operation.log.handler;

import org.springframework.scheduling.annotation.Async;

/**
 * {@code OperationLogHandler}接口定义了操作日志推送的默认行为。
 *
 * <p>该接口用于处理操作日志的记录与推送。具体的实现类可以根据业务需求实现不同的日志处理逻辑。</p>
 *
 * <p>方法{@link #push(String, String, String, String, String, String, Throwable)}使用了{@link Async}注解，
 * 表示该方法会被异步调用，适合在后台执行耗时的日志处理任务。</p>
 *
 * <p>接口的主要功能包括：</p>
 * <ul>
 *     <li>接收日志的各种信息，如追踪链路ID、日志类型、操作人信息等。</li>
 *     <li>处理并推送日志内容，支持在出现异常时记录错误堆栈信息。</li>
 * </ul>
 *
 * <p>实现类可以根据实际需求定义如何将日志推送到目标存储或服务。</p>
 *
 * @author francis
 * @since 1.0.0
 * @version 1.0.0
 */
public interface OperationLogHandler {

    /**
     * 处理并推送操作日志。
     *
     * <p>该方法异步执行，主要用于记录操作日志并将其推送到日志存储或处理系统中。</p>
     *
     * @param traceId      日志追踪链路ID，用于标识一条日志的唯一标识符
     * @param logLargeType 业务日志的大类型，表示日志的分类
     * @param logSmallType 业务日志的小类型，进一步细分日志的分类
     * @param operatorId   操作人的唯一标识符
     * @param operatorName 操作人的名称
     * @param content      操作日志的具体内容
     * @param e            错误堆栈信息，如果操作过程中出现异常，则记录该异常信息，否则为{@code null}
     */
    @Async
    void push(String traceId, String logLargeType, String logSmallType, String operatorId, String operatorName, String content, Throwable e);

}
