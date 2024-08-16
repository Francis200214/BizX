package com.biz.operation.log.configuration;

import com.biz.operation.log.OperationLogAspect;
import com.biz.operation.log.handler.DefaultOperationLogHandler;
import com.biz.operation.log.handler.OperationLogHandler;
import com.biz.operation.log.handler.OperationLogHandlerFactory;
import com.biz.operation.log.processor.OperationLogProcessor;
import com.biz.operation.log.recorder.OperationLogRecorder;
import com.biz.operation.log.replace.ContentReplacer;
import com.biz.operation.log.replace.DefaultContentReplacer;
import com.biz.operation.log.store.DefaultOperationLogUserContext;
import com.biz.operation.log.store.OperationLogUserContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，负责操作日志的自动配置。
 *
 * <p>该配置类提供了默认的操作日志处理器、用户存储服务以及日志记录器的Bean。</p>
 *
 * <p>当配置文件中设置了 {@code biz.operation.log.enabled=true} 时，
 * 本配置类中的Bean才会生效。</p>
 *
 * <p>此配置类主要包含以下几个核心Bean：</p>
 * <ul>
 *     <li>{@link OperationLogUserContext}：用户存储服务接口的默认实现。</li>
 *     <li>{@link OperationLogHandler}：操作日志处理器接口的默认实现。</li>
 *     <li>{@link ContentReplacer}：日志内容替换器的默认实现。</li>
 *     <li>{@link OperationLogHandlerFactory}：操作日志处理器工厂。</li>
 *     <li>{@link OperationLogRecorder}：操作日志记录器。</li>
 *     <li>{@link OperationLogProcessor}：日志处理前的操作处理器。</li>
 *     <li>{@link OperationLogAspect}：操作日志拦截器，用于记录操作日志。</li>
 * </ul>
 *
 * @see DefaultOperationLogHandler
 * @see DefaultOperationLogUserContext
 * @see OperationLogAspect
 * @author francis
 * @since 1.0.0
 * @version 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix = "biz.operation.log", name = "enabled", havingValue = "true")
public class BizXOperationLogConfiguration {

    /**
     * 创建一个默认的 OperationLogUserContext 实例。
     *
     * <p>如果 Spring 上下文中不存在自定义的 OperationLogUserContext Bean，
     * 则会使用该方法提供的默认实现。</p>
     *
     * @return 默认的 OperationLogUserContext 实例
     * @see DefaultOperationLogUserContext
     */
    @Bean
    @ConditionalOnMissingBean(OperationLogUserContext.class)
    public OperationLogUserContext operationLogUserStore() {
        return new DefaultOperationLogUserContext();
    }

    /**
     * 创建一个默认的 OperationLogHandler 实例。
     *
     * <p>如果 Spring 上下文中不存在自定义的 OperationLogHandler Bean，
     * 则会使用该方法提供的默认实现。</p>
     *
     * @return 默认的 OperationLogHandler 实例
     * @see DefaultOperationLogHandler
     */
    @Bean
    @ConditionalOnMissingBean(OperationLogHandler.class)
    public OperationLogHandler operationLogHandler() {
        return new DefaultOperationLogHandler();
    }

    /**
     * 创建一个 ContentReplacer 实例，用于替换日志内容中的信息。
     *
     * @return 默认的 ContentReplacer 实例
     * @see DefaultContentReplacer
     */
    @Bean
    public ContentReplacer contentReplacer() {
        return new DefaultContentReplacer();
    }

    /**
     * 创建一个 OperationLogHandlerFactory 实例，用于管理和提供操作日志处理器。
     *
     * @param operationLogHandler 默认的操作日志处理器
     * @return OperationLogHandlerFactory 实例
     * @see OperationLogHandlerFactory
     */
    @Bean
    public OperationLogHandlerFactory operationLogHandlerFactory(OperationLogHandler operationLogHandler) {
        return new OperationLogHandlerFactory(operationLogHandler);
    }

    /**
     * 创建一个 OperationLogRecorder 实例，用于记录操作日志。
     *
     * @param operationLogHandlerFactory 日志处理器工厂
     * @param operationLogUserContext 用户存储服务
     * @return OperationLogRecorder 实例
     * @see OperationLogRecorder
     */
    @Bean
    public OperationLogRecorder operationLogRecorder(OperationLogHandlerFactory operationLogHandlerFactory, OperationLogUserContext operationLogUserContext) {
        return new OperationLogRecorder(operationLogHandlerFactory, operationLogUserContext);
    }

    /**
     * 创建一个 OperationLogProcessor 实例，用于处理日志记录前的操作。
     *
     * @param operationLogRecorder 日志记录器
     * @param contentReplacer 内容替换器
     * @return OperationLogProcessor 实例
     * @see OperationLogProcessor
     */
    @Bean
    public OperationLogProcessor operationLogProcessor(OperationLogRecorder operationLogRecorder, ContentReplacer contentReplacer) {
        return new OperationLogProcessor(operationLogRecorder, contentReplacer);
    }

    /**
     * 创建一个 OperationLogAspect 实例，用于拦截和记录操作日志。
     *
     * @param operationLogProcessor 日志处理器
     * @return OperationLogAspect 实例
     * @see OperationLogAspect
     */
    @Bean
    public OperationLogAspect operationLogAspect(OperationLogProcessor operationLogProcessor) {
        return new OperationLogAspect(operationLogProcessor);
    }

}
