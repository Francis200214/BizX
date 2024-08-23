package com.biz.operation.log.handler;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code DefaultOperationLogHandler}类是{@link OperationLogHandler}接口的默认实现。
 *
 * <p>该实现类负责将操作日志记录到日志系统中。当日志级别设置为调试模式时，日志将包含详细的操作信息。
 * 如果在操作过程中出现异常，该异常信息也会被记录。</p>
 *
 * <p>类的主要职责包括：</p>
 * <ul>
 *     <li>接收并处理操作日志的各种信息，包括追踪链路ID、日志类型、操作人信息等。</li>
 *     <li>将日志内容输出到日志系统中，支持记录异常信息。</li>
 * </ul>
 *
 * <p>实现细节：</p>
 * <ul>
 *     <li>当没有异常时，记录操作日志的追踪链路ID、分类、操作人信息和内容。</li>
 *     <li>当发生异常时，除了记录上述信息外，还会记录异常的消息内容。</li>
 * </ul>
 *
 * <p>通过使用{@link Slf4j}注解，类中日志记录功能由SLF4J提供。</p>
 *
 * @author francis
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class DefaultOperationLogHandler implements OperationLogHandler {

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
        if (log.isDebugEnabled()) {
            if (e == null) {
                log.debug("traceId: {}, category: {}, subcategory: {}, operatorId: {}, operatorName: {}, content: {}", traceId, category, subcategory, operatorId, operatorName, content);
            } else {
                log.debug("traceId: {}, category: {}, subcategory: {}, operatorId: {}, operatorName: {}, content: {}, e: {}", traceId, category, subcategory, operatorId, operatorName, content, e.getMessage());
            }
        }
    }

}
