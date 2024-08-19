package com.biz.trace.store;

/**
 * 日志追踪存储接口。
 *
 * <p>该接口定义了用于管理日志追踪Id的方法。实现类应提供获取和移除当前线程的追踪Id的具体逻辑。</p>
 *
 * <p>此接口通常用于在分布式系统中，通过跟踪请求链路来实现日志的统一管理和追踪。每个线程可以拥有独立的追踪Id，便于在复杂的系统中定位和分析请求路径。</p>
 *
 * <pre>
 * 示例使用：
 * {@code
 * TraceStoreService traceStoreService = ...;
 * String traceId = traceStoreService.getTraceId();
 * traceStoreService.removeTraceId();
 * }
 * </pre>
 *
 * @see com.biz.trace.interceptor.TraceInterceptor
 * @see DefaultTraceStoreServiceImpl
 *
 * @author francis
 * @since 1.0.1
 **/
public interface TraceStoreService {

    /**
     * 获取当前线程的追踪Id。
     *
     * <p>实现类应确保每个线程都有独立的追踪Id，以便在多线程环境中能够正确地追踪和关联日志。</p>
     *
     * @return 当前线程的追踪Id
     */
    String getTraceId();

    /**
     * 移除当前线程的追踪Id。
     *
     * <p>该方法通常在请求处理完成后调用，以确保追踪Id不会在线程间泄露，并且能够正确清理资源。</p>
     */
    void removeTraceId();

}
