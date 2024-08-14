package com.biz.operation.log.configuration;

import com.biz.operation.log.OperationLogAspect;
import com.biz.operation.log.handler.DefaultOperationLogHandler;
import com.biz.operation.log.handler.OperationLogHandler;
import com.biz.operation.log.handler.OperationLogHandlerFactory;
import com.biz.operation.log.manager.OperationLogManager;
import com.biz.operation.log.recorder.LogRecorder;
import com.biz.operation.log.replace.ContentReplacer;
import com.biz.operation.log.replace.DefaultContentReplacer;
import com.biz.operation.log.store.DefaultOperationLogUserStore;
import com.biz.operation.log.store.OperationLogUserStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * 配置类，负责操作日志的自动配置。
 *
 * <p>该配置类提供了默认的操作日志处理器、用户存储服务以及日志记录器的Bean。</p>
 *
 * @see DefaultOperationLogHandler
 * @see DefaultOperationLogUserStore
 * @see LogRecorder
 * @see OperationLogAspect
 */
@Configuration
@ConditionalOnProperty(prefix = "biz.operation.log", name = "enabled", havingValue = "true")
public class BizXOperationLogConfiguration {

    /**
     * 创建一个默认的LocalUserStoreService实例。
     *
     * <p>如果Spring上下文中不存在自定义的LocalUserStoreService Bean，
     * 则会使用该方法提供的默认实现。</p>
     *
     * @return 默认的LocalUserStoreService实例
     * @see DefaultOperationLogUserStore
     */
    @Bean
    @ConditionalOnMissingBean(OperationLogUserStore.class)
    public OperationLogUserStore localUserStoreService() {
        return new DefaultOperationLogUserStore();
    }

    /**
     * 创建一个默认的OperationLogHandler实例。
     *
     * <p>如果Spring上下文中不存在自定义的OperationLogHandler Bean，
     * 则会使用该方法提供的默认实现。</p>
     *
     * @return 默认的OperationLogHandler实例
     * @see DefaultOperationLogHandler
     */
    @Bean
    @ConditionalOnMissingBean(OperationLogHandler.class)
    public OperationLogHandler operationLogHandler() {
        return new DefaultOperationLogHandler();
    }


    /**
     * 创建一个ContentReplacer实例，用于替换日志内容中的信息。
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ContentReplacer.class)
    public ContentReplacer contentReplacer() {
        return new DefaultContentReplacer();
    }


    @Bean
    public OperationLogHandlerFactory operationLogHandlerFactory(OperationLogHandler operationLogHandler) {
        return new OperationLogHandlerFactory(operationLogHandler);
    }

    /**
     * 创建一个LogRecorder实例，用于记录操作日志。
     *
     * <p>该方法会依赖注入OperationLogHandler实例，
     * 并通过LogRecorder进行操作日志的记录。</p>
     *
     * @param operationLogHandler 操作日志处理器
     * @return LogRecorder实例
     * @see LogRecorder
     */
    @Bean
    public LogRecorder logRecorder(OperationLogHandlerFactory operationLogHandlerFactory) {
        return new LogRecorder(operationLogHandlerFactory);
    }

    @Bean
    public OperationLogManager operationLogManager(LogRecorder logRecorder, OperationLogUserStore operationLogUserStore, ContentReplacer contentReplacer) {
        return new OperationLogManager(logRecorder, operationLogUserStore, contentReplacer);
    }


    /**
     * 创建一个操作日志切面，用于拦截和记录操作日志。
     *
     * <p>该切面会在指定的操作发生时拦截并记录日志，依赖注入的
     * LogRecorder和LocalUserStoreService实例会用于记录和存储日志信息。</p>
     *
     * @param logRecorder           日志记录器
     * @param operationLogUserStore 本地用户存储服务
     * @param contentReplacer       内容替换器
     * @return OperationLogAspect实例
     * @see OperationLogAspect
     */
    @Bean
    public OperationLogAspect operationLogAspect(@Lazy OperationLogManager operationLogManager) {
        return new OperationLogAspect(operationLogManager);
    }
}
