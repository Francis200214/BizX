package com.biz.operation.log.recorder;

import com.biz.operation.log.OperationLog;
import com.biz.operation.log.handler.OperationLogHandler;
import com.biz.operation.log.handler.OperationLogHandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;

/**
 * 日志记录器
 *
 * @author francis
 * @since 2024-05-31 16:30
 **/
@Slf4j
public class LogRecorder {


    private final OperationLogHandlerFactory operationLogHandlerFactory;

    public LogRecorder(OperationLogHandlerFactory operationLogHandlerFactory) throws BeansException {
        this.operationLogHandlerFactory = operationLogHandlerFactory;
    }

    /**
     * 记录日志
     *
     * @param traceId      日志追踪链路Id
     * @param operationLog 业务日志
     * @param operatorId   操作人
     * @param operatorName 操作人名称
     * @param content      操作日志内容
     * @param e            错误堆栈信息（只有当有错误信息时才会传递该参数，否则为null）
     */
    public void record(String traceId, OperationLog operationLog, String operatorId, String operatorName, String content, Throwable e) {
        OperationLogHandler operationLogHandler = operationLogHandlerFactory.getOperationLogHandler(operationLog.category(), operationLog.subcategory());
        operationLogHandler.push(traceId, operationLog.category(), operationLog.subcategory(), operatorId, operatorName, content, e);
    }


}
