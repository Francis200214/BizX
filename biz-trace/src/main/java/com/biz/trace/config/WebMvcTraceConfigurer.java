package com.biz.trace.config;

import com.biz.trace.interceptor.TraceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Mvc Trace 配置类，负责注册和管理链路追踪拦截器。
 *
 * <p>该配置类根据应用环境配置决定是否启用链路追踪拦截器。通过配置项 <code>biz.trace.enabled</code> 来控制链路追踪功能的启用与禁用。</p>
 *
 * <p>当配置项 <code>biz.trace.enabled=true</code> 时，Spring 容器将加载此配置类，并将 {@link TraceInterceptor} 注册到 {@link InterceptorRegistry} 中，从而为所有 HTTP 请求添加链路追踪功能。</p>
 *
 * <pre>
 * 示例配置：
 * biz.trace.enabled=true
 * </pre>
 *
 * @author francis
 * @see WebMvcConfigurer
 * @see TraceInterceptor
 * @since 2023/4/1 16:30
 */
@Slf4j
@ConditionalOnProperty(prefix = "biz.trace", name = "enabled", havingValue = "true")
public class WebMvcTraceConfigurer implements WebMvcConfigurer {

    private final TraceInterceptor traceInterceptor;

    /**
     * 构造函数，注入 {@link TraceInterceptor} 实例。
     *
     * @param traceInterceptor 注入的 {@link TraceInterceptor} 实例
     */
    public WebMvcTraceConfigurer(TraceInterceptor traceInterceptor) {
        this.traceInterceptor = traceInterceptor;
    }

    /**
     * 注册拦截器。将 {@link TraceInterceptor} 注册到 {@link InterceptorRegistry} 中，
     * 以拦截所有 HTTP 请求并添加链路追踪的逻辑。
     *
     * @param registry 拦截器注册器，用于注册自定义的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceInterceptor).addPathPatterns("/**");
    }

}
