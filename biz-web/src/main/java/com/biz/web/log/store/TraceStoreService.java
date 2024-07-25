package com.biz.web.log.store;

/**
 * 日志追踪存储
 *
 * @author francis
 * @create 2024-07-04 16:32
 **/
public interface TraceStoreService {

    /**
     * 获取当前线程的traceId
     */
    String getTraceId();

    /**
     * 移除当前线程的traceId
     */
    void removeTraceId();

}
