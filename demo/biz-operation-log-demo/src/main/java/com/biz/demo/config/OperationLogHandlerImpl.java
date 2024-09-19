package com.biz.demo.config;

import com.biz.operation.log.handler.OperationLogHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 操作日志接收器
 *
 * @author francis
 * @create 2024-09-19
 **/
@Slf4j
@Component
public class OperationLogHandlerImpl implements OperationLogHandler {

    @Override
    public void push(String traceId, String category, String subcategory, String operatorId, String operatorName, String content, Throwable e) {
        if (e == null) {
            log.info("[操作日志接收] traceId: {}, category: {}, subcategory: {}, operatorId: {}, operatorName: {}, content: {}", traceId, category, subcategory, operatorId, operatorName, content);
        } else {
            log.info("[操作日志接收] traceId: {}, category: {}, subcategory: {}, operatorId: {}, operatorName: {}, content: {}, e: {}", traceId, category, subcategory, operatorId, operatorName, content, e.getMessage());
        }
    }

}
