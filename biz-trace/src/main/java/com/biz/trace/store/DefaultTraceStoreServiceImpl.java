package com.biz.trace.store;

import com.biz.trace.common.TraceConstant;
import com.biz.trace.id.DefaultTraceIdServiceImpl;
import com.biz.trace.id.TraceIdService;
import com.biz.trace.interceptor.TraceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * 默认的日志追踪存储实现类。
 *
 * <p>该类实现了 {@link TraceStoreService} 接口，并通过 {@link MDC} 存储当前线程的追踪Id。
 * 在多线程环境中，该实现确保每个线程都有独立的追踪Id，以便于日志的追踪和管理。</p>
 *
 * @see TraceStoreService
 * @see DefaultTraceIdServiceImpl
 * @author francis
 * @since 1.0.1
 **/
@Slf4j
public class DefaultTraceStoreServiceImpl implements TraceStoreService {


    /**
     * 将指定的追踪Id存储在当前线程中。
     *
     * <p>该方法被 {@link TraceInterceptor} 调用，用于将请求头中的追踪Id存储在 {@link MDC} 中。</p>
     *
     * @param traceId 要存储的追踪Id
     */
    @Override
    public void put(String traceId) {
        MDC.put(TraceConstant.TRACE_ID, traceId);
    }

    /**
     * 获取当前线程的追踪Id。
     *
     * <p>如果当前线程没有追踪Id，则通过 {@link TraceIdService} 生成一个新的追踪Id并存储在 {@link MDC} 中。</p>
     *
     * @return 当前线程的追踪Id
     */
    @Override
    public String get() {
        return MDC.get(TraceConstant.TRACE_ID);
    }

    /**
     * 移除当前线程的追踪Id。
     *
     * <p>该方法在请求处理完成后调用，以确保 {@link MDC} 中存储的追踪Id不会在线程间泄露。</p>
     */
    @Override
    public void remove() {
        try {
            MDC.remove(TraceConstant.TRACE_ID);
        } catch (Exception e) {
            log.error("移除 TraceIdHolder 失败", e);
            throw new RuntimeException(e);
        }
    }

}
