package com.biz.trace.config;

import com.biz.trace.id.DefaultTraceIdServiceImpl;
import com.biz.trace.id.TraceIdService;
import com.biz.trace.interceptor.TraceInterceptor;
import com.biz.trace.store.DefaultTraceStoreService;
import com.biz.trace.store.TraceStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 链路追踪配置类。
 *
 * <p>该配置类负责初始化和配置链路追踪相关的服务组件。仅当配置项 <code>biz.trace.enabled=true</code> 时，该配置类才会生效。</p>
 *
 * <p>默认情况下，如果没有其他自定义的 {@link TraceStoreService} 或 {@link TraceIdService} 实现类，
 * 本类将提供 {@link DefaultTraceStoreService} 和 {@link DefaultTraceIdServiceImpl} 的默认实现。</p>
 *
 * <pre>
 * 示例配置：
 * biz.trace.enabled=true
 * </pre>
 *
 * <p>此类依赖于以下配置项：</p>
 * <ul>
 *     <li><code>biz.trace.enabled</code> - 是否启用链路追踪功能</li>
 * </ul>
 *
 * @author francis
 * @since 2024-08-09 09:00
 * @see TraceStoreService
 * @see TraceIdService
 * @see DefaultTraceStoreService
 * @see DefaultTraceIdServiceImpl
 */
@Slf4j
@ConditionalOnProperty(prefix = "biz.trace", name = "enabled", havingValue = "true")
@Configuration
public class TraceConfigurer {

    /**
     * 创建并返回 {@link TraceInterceptor} 的 Bean 实例。
     * 该拦截器用于在所有 HTTP 请求中添加链路追踪的逻辑。
     *
     * @param traceStoreService 注入的 {@link TraceStoreService} 实例
     * @return TraceInterceptor Bean 实例
     */
    @Bean
    @DependsOn(value = "traceStoreService")
    public TraceInterceptor traceInterceptor(TraceStoreService traceStoreService) {
        return new TraceInterceptor(traceStoreService);
    }

    /**
     * 提供 {@link TraceStoreService} 的默认实现。
     *
     * <p>如果 Spring 上下文中不存在其他 {@link TraceStoreService} 的 Bean，则该方法将返回 {@link DefaultTraceStoreService} 的实例。</p>
     *
     * @param traceIdService 注入的 {@link TraceIdService} 实例
     * @return 默认的 {@link TraceStoreService} 实现
     */
    @Bean
    @ConditionalOnMissingBean(TraceStoreService.class)
    @DependsOn(value = "traceIdService")
    public TraceStoreService traceStoreService(TraceIdService traceIdService) {
        return new DefaultTraceStoreService(traceIdService);
    }

    /**
     * 提供 {@link TraceIdService} 的默认实现。
     *
     * <p>如果 Spring 上下文中不存在其他 {@link TraceIdService} 的 Bean，则该方法将返回 {@link DefaultTraceIdServiceImpl} 的实例。</p>
     *
     * @return 默认的 {@link TraceIdService} 实现
     */
    @Bean
    @ConditionalOnMissingBean(TraceIdService.class)
    public TraceIdService traceIdService() {
        return new DefaultTraceIdServiceImpl();
    }
}
