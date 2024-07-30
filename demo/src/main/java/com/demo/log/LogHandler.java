package com.demo.log;

import com.biz.web.log.operation.DefaultOperationLogHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 添加用户信息日志
 *
 * @author francis
 * @create 2024-06-03 13:51
 **/
@Service
@Slf4j
public class LogHandler implements DefaultOperationLogHandler {

    @Override
    public void push(String traceId, String logLargeType, String logSmallType, String operatorId, String operatorName, String content, Throwable e) {
        if (e == null) {
            log.info("traceId: {} logLargeType: {} logSmallType: {} operatorId: {} operatorName: {} content: {}", traceId, logLargeType, logSmallType, operatorId, operatorName, content);
        } else {
            log.info("traceId: {} logLargeType: {} logSmallType: {} operatorId: {} operatorName: {} content: {} e: ", traceId, logLargeType, logSmallType, operatorId, operatorName, content, e);
        }
    }

}
