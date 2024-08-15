package com.biz.operation.log.recorder;

import com.biz.operation.log.OperationLog;
import com.biz.operation.log.handler.OperationLogHandlerFactory;
import com.biz.operation.log.store.OperationLogUserSession;
import lombok.Setter;

/**
 * {@code OperationLogRecorder}类负责记录操作日志。
 *
 * <p>该类通过整合日志处理工厂{@link OperationLogHandlerFactory}和用户存储{@link OperationLogUserSession}，
 * 实现了操作日志的记录功能。日志内容和操作信息可以通过该类进行动态设置和记录。</p>
 *
 * <p>类的主要功能包括设置日志内容、操作日志对象，并将日志信息记录到相应的日志处理器中。</p>
 *
 * <p>主要方法包括：</p>
 * <ul>
 *     <li>{@link #record(String, Throwable)}：记录操作日志，并处理可能的异常。</li>
 * </ul>
 *
 * @author francis
 * @since 1.0.0
 * @version 1.0.0
 */
public class OperationLogRecorder {

    /**
     * 操作日志处理工厂，用于获取具体的操作日志处理器。
     */
    private final OperationLogHandlerFactory operationLogHandlerFactory;

    /**
     * 操作日志用户存储，用于获取当前操作用户的信息。
     */
    private final OperationLogUserSession operationLogUserSession;

    /**
     * 日志内容，包含需要记录的操作日志信息。
     */
    @Setter
    private String content;

    /**
     * 操作日志对象，包含日志的分类和其他相关信息。
     */
    @Setter
    private OperationLog operationLog;

    /**
     * 构造一个新的{@code OperationLogRecorder}实例。
     *
     * @param operationLogHandlerFactory 操作日志处理工厂，用于获取具体的日志处理器
     * @param operationLogUserSession 操作日志用户存储，用于获取当前操作用户的信息
     */
    public OperationLogRecorder(OperationLogHandlerFactory operationLogHandlerFactory, OperationLogUserSession operationLogUserSession) {
        this.operationLogHandlerFactory = operationLogHandlerFactory;
        this.operationLogUserSession = operationLogUserSession;
    }

    /**
     * 记录操作日志，并处理可能的异常。
     *
     * <p>该方法从{@link OperationLogUserSession}获取当前操作用户的信息，并通过
     * {@link OperationLogHandlerFactory}获取相应的日志处理器，最终记录日志。</p>
     *
     * @param traceId 跟踪ID，用于标识特定操作的唯一标识符
     * @param e 如果操作中发生异常，该异常对象将被记录在日志中
     */
    public void record(String traceId, Throwable e) {
        String operatorId = operationLogUserSession.getOperationUserId();
        String operatorName = operationLogUserSession.getOperationUserName();
        operationLogHandlerFactory.getOperationLogHandler(operationLog.category(), operationLog.subcategory())
                .push(traceId, operationLog.category(), operationLog.subcategory(), operatorId, operatorName, content, e);
    }
}
