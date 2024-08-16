package com.demo.log;

import com.biz.operation.log.handler.OperationLogHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 添加用户信息日志
 *
 * @author francis
 * @since 1.0.1
 **/
@Component
@Slf4j
public class LogHandler implements OperationLogHandler {

    @Override
    public void push(String traceId, String logLargeType, String logSmallType, String operatorId, String operatorName, String content, Throwable e) {
        if (e == null) {
            log.info("traceId: {} logLargeType: {} logSmallType: {} operatorId: {} operatorName: {} content: {}", traceId, logLargeType, logSmallType, operatorId, operatorName, content);
        } else {
            log.info("traceId: {} logLargeType: {} logSmallType: {} operatorId: {} operatorName: {} content: {} e: ", traceId, logLargeType, logSmallType, operatorId, operatorName, content, e);
        }
    }

}
