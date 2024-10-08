package com.biz.trace.interceptor;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;
import com.biz.trace.id.TraceIdService;
import com.biz.trace.store.TraceStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志追踪拦截器。
 *
 * <p>该拦截器实现了 {@link HandlerInterceptor} 接口，用于在处理每个 HTTP 请求时生成并管理日志追踪Id。</p>
 *
 * <p>拦截器在请求进入时首先检查请求头中是否包含特定的追踪Id（通过 {@code X-TRACE-ID-HEADER} 传递）。
 * 如果请求头中没有该追踪Id，则通过 {@link TraceIdService} 生成一个新的追踪Id，并将其存储在 {@link TraceStoreService} 中，
 * 便于日志系统使用。请求完成后，追踪Id会被移除，以确保线程安全和资源清理。</p>
 *
 * @author francis
 * @see HandlerInterceptor
 * @see TraceStoreService
 * @see BizXBeanUtils
 * @since 1.0.1
 **/
@Slf4j
public class TraceInterceptor implements HandlerInterceptor, Ordered {

    /**
     * 用于在请求头中传递追踪Id的键。
     */
    private static final String TRACE_ID_KEY = "X-TRACE-ID-HEADER";

    /**
     * 用于存储和获取追踪Id的服务。
     */
    private final TraceStoreService traceStoreService;

    /**
     * 用于生成日志追踪Id的服务。
     */
    private final TraceIdService traceIdService;


    /**
     * 构造函数，允许通过参数直接注入 {@link TraceStoreService} 实例。
     *
     * @param traceStoreService 注入的 {@link TraceStoreService} 实例
     */
    public TraceInterceptor(TraceStoreService traceStoreService, TraceIdService traceIdService) {
        this.traceStoreService = traceStoreService;
        this.traceIdService = traceIdService;
    }

    /**
     * 在请求处理之前执行，将生成的追踪Id存入 {@link TraceStoreService} 中。
     *
     * <p>如果请求头中包含 {@code X-TRACE-ID-HEADER}，则直接使用该值作为追踪Id。
     * 否则，生成一个新的追踪Id，并存入 {@link TraceStoreService} 中。</p>
     *
     * @param request  当前的 HTTP 请求
     * @param response 当前的 HTTP 响应
     * @param handler  被调用的处理器
     * @return 是否继续处理请求
     * @throws Exception 如果发生任何错误
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceIdHeader = request.getHeader(TRACE_ID_KEY);
        if (Common.isBlank(traceIdHeader)) {
            traceStoreService.put(traceIdService.getId());
        } else {
            traceStoreService.put(traceIdHeader);
        }
        return true;
    }

    /**
     * 在请求处理之后但在视图渲染之前执行。本方法未实现任何逻辑，但保留了方法体以便扩展。
     *
     * @param request      当前的 HTTP 请求
     * @param response     当前的 HTTP 响应
     * @param handler      被调用的处理器
     * @param modelAndView 请求处理后的视图
     * @throws Exception 如果发生任何错误
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在请求完成后执行，清理 {@link TraceStoreService} 中的追踪Id。
     *
     * @param request  当前的 HTTP 请求
     * @param response 当前的 HTTP 响应
     * @param handler  被调用的处理器
     * @param ex       请求处理过程中发生的异常，如果有则传入
     * @throws Exception 如果发生任何错误
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        traceStoreService.remove();
    }

    /**
     * 获取拦截器的顺序。
     *
     * <p>该拦截器的顺序被设置为99，以确保其在大多数其他拦截器之后执行。</p>
     *
     * @return 拦截器的顺序值
     */
    @Override
    public int getOrder() {
        return 99;
    }

}
