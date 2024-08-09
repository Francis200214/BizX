package com.biz.trace.store;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;
import com.biz.trace.id.DefaultTraceIdServiceImpl;
import com.biz.trace.id.TraceIdService;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认的日志追踪存储实现类。
 *
 * <p>该类实现了 {@link TraceStoreService} 接口，并通过 {@link ThreadLocal} 存储当前线程的追踪Id。
 * 在多线程环境中，该实现确保每个线程都有独立的追踪Id，以便于日志的追踪和管理。</p>
 *
 * <p>此外，该类在构造时通过 {@link BizXBeanUtils} 尝试获取 {@link TraceIdService} 的 Bean 实例。</p>
 *
 * @see TraceStoreService
 * @see TraceIdService
 * @see DefaultTraceIdServiceImpl
 * @see ThreadLocal
 * @see BizXBeanUtils
 *
 * @author francis
 * @since 2024-07-04 16:34
 **/
@Slf4j
public class DefaultTraceStoreServiceImpl implements TraceStoreService {

    /**
     * 当前线程追踪Id的存储容器。
     *
     * <p>使用 {@link ThreadLocal} 来确保每个线程独立的追踪Id。</p>
     */
    private static final ThreadLocal<String> traceIdHolder = new ThreadLocal<>();

    /**
     * 用于生成日志追踪Id的服务。
     */
    private final TraceIdService traceIdService;

    /**
     * 构造函数，尝试从 Spring 容器中获取 {@link TraceIdService} 的 Bean 实例。
     */
    public DefaultTraceStoreServiceImpl(TraceIdService traceIdService) {
        this.traceIdService = traceIdService;
    }

    /**
     * 获取当前线程的追踪Id。
     *
     * <p>如果当前线程没有追踪Id，则通过 {@link TraceIdService} 生成一个新的追踪Id并存储在 {@link ThreadLocal} 中。</p>
     *
     * @return 当前线程的追踪Id
     */
    @Override
    public String getTraceId() {
        String traceId = traceIdHolder.get();
        if (Common.isBlank(traceId)) {
            traceIdHolder.set(traceIdService.getId());
            return traceIdHolder.get();
        }
        return traceId;
    }

    /**
     * 移除当前线程的追踪Id。
     *
     * <p>该方法在请求处理完成后调用，以确保 {@link ThreadLocal} 中存储的追踪Id不会在线程间泄露。</p>
     */
    @Override
    public void removeTraceId() {
        try {
            traceIdHolder.remove();
        } catch (Exception e) {
            log.error("移除 TraceIdHolder 失败", e);
            throw new RuntimeException(e);
        }
    }

}
