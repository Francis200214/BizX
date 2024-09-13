package com.demo.log;

import com.biz.operation.log.handler.OperationLogHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 接收操作日志信息
 *
 * @author francis
 **/
@Component
@Slf4j
public class LogHandler implements OperationLogHandler {

    /**
     * 处理并记录操作日志。
     *
     * <p>根据是否有异常信息，记录操作日志的详细内容。当启用调试级别日志时，该方法会输出日志。</p>
     *
     * @param traceId      日志追踪链路ID，用于标识一条日志的唯一标识符
     * @param category     业务日志的大类型，表示日志的分类
     * @param subcategory  业务日志的小类型，进一步细分日志的分类
     * @param operatorId   操作人的唯一标识符
     * @param operatorName 操作人的名称
     * @param content      操作日志的具体内容
     * @param e            错误堆栈信息，如果操作过程中出现异常，则记录该异常信息，否则为{@code null}
     */
    @Override
    public void push(String traceId, String category, String subcategory, String operatorId, String operatorName, String content, Throwable e) {
        if (e == null) {
            log.info("traceId: {} category: {} subcategory: {} operatorId: {} operatorName: {} content: {}", traceId, category, subcategory, operatorId, operatorName, content);
        } else {
            log.info("traceId: {} category: {} subcategory: {} operatorId: {} operatorName: {} content: {} e: ", traceId, category, subcategory, operatorId, operatorName, content, e);
        }
    }

}
