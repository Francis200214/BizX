package com.biz.operation.log.handler;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认操作日志推送接收
 *
 * @author francis
 * @since 2024-08-13 14:50
 **/
@Slf4j
public class DefaultOperationLogHandler implements OperationLogHandler {

    @Override
    public void push(String traceId, String logLargeType, String logSmallType, String operatorId, String operatorName, String content, Throwable e) {
        if (log.isDebugEnabled()) {
            if (e == null) {
                log.debug("traceId: {}, category: {}, subcategory: {}, operatorId: {}, operatorName: {}, content: {}", traceId, logLargeType, logSmallType, operatorId, operatorName, content);
            } else {
                log.debug("traceId: {}, category: {}, subcategory: {}, operatorId: {}, operatorName: {}, content: {}, e: {}", traceId, logLargeType, logSmallType, operatorId, operatorName, content, e.getMessage());
            }
        }
    }

}
