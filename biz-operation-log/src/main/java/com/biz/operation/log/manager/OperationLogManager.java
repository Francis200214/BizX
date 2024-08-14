package com.biz.operation.log.manager;

import com.biz.common.spel.SpELUtils;
import com.biz.operation.log.OperationLog;
import com.biz.operation.log.recorder.LogRecorder;
import com.biz.operation.log.replace.ContentReplacer;
import com.biz.operation.log.store.OperationLogUserStore;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 日志管理和处理中心
 *
 * @author francis
 * @since 2024-08-14 09:43
 **/
@Slf4j
public class OperationLogManager {

    /**
     * 日志记录器，用于记录操作日志。
     */
    private final LogRecorder logRecorder;

    /**
     * 本地用户存储服务，用于获取当前操作用户的信息。
     */
    private final OperationLogUserStore operationLogUserStore;

    /**
     * 内容替换器，用于动态替换日志内容中的占位符。
     */
    private final ContentReplacer contentReplacer;

    /**
     * 当前操作方法参数的日志内容，使用ThreadLocal确保线程安全。
     */
    private static final ThreadLocal<String> contentHolder = new ThreadLocal<>();

    /**
     * 当前操作方法参数的日志注解，使用ThreadLocal确保线程安全。
     */
    private static final ThreadLocal<OperationLog> operationLogHolder = new ThreadLocal<>();

    public OperationLogManager(LogRecorder logRecorder, OperationLogUserStore operationLogUserStore, ContentReplacer contentReplacer) {
        this.logRecorder = logRecorder;
        this.operationLogUserStore = operationLogUserStore;
        this.contentReplacer = contentReplacer;
    }

    /**
     * 处理参数，替换占位符，并记录日志。
     *
     * @param signature
     * @param args
     * @param operationLog
     */
    public void handlerParameter(MethodSignature signature, Object[] args, OperationLog operationLog) {
        StandardEvaluationContext context = SpELUtils.createContext(signature.getParameterNames(), args);
        context.setVariable("operationName", operationLogUserStore.getOperationUserName());
        context.setVariable("category", operationLog.category());
        context.setVariable("subcategory", operationLog.subcategory());

        String content = contentReplacer.replace(operationLog.content(), context);
        contentHolder.set(content);
        operationLogHolder.set(operationLog);
    }

    public void push() {
        this.push();
    }

    public void push(Throwable e) {
        logRecorder.record(null, operationLogHolder.get(), operationLogUserStore.getOperationUserId(), operationLogUserStore.getOperationUserName(), contentHolder.get(), e);
        this.flushThreadLocal();
    }

    /**
     * 清空当前线程中的ThreadLocal变量。
     */
    public void flushThreadLocal() {
        operationLogUserStore.removeAll();
        contentHolder.remove();
        operationLogHolder.remove();
    }

    private void recordLog(String traceId, OperationLog operationLog, String content, Throwable e) {
        String operatorId = operationLogUserStore.getOperationUserId();
        String operatorName = operationLogUserStore.getOperationUserName();
        logRecorder.record(traceId, operationLog, operatorId, operatorName, content, e);
    }

}
