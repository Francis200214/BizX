package com.biz.operation.log.handler;

import org.springframework.scheduling.annotation.Async;

/**
 * 默认操作日志推送接口
 *
 * @author francis
 * @since 2024-07-23 10:27
 **/
public interface OperationLogHandler {

    /**
     * 日志处理
     *
     * @param traceId      日志追踪链路Id
     * @param logLargeType 业务日志大类型
     * @param logSmallType 业务日志小类型
     * @param operatorId   操作人
     * @param operatorName 操作人名称
     * @param content      操作日志内容
     * @param e            错误堆栈信息（只有当有错误信息时才会传递该参数，否则为null）
     */
    @Async
    void push(String traceId, String logLargeType, String logSmallType, String operatorId, String operatorName, String content, Throwable e);

}
