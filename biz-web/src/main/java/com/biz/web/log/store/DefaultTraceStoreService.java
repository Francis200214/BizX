package com.biz.web.log.store;

import com.biz.common.utils.Common;
import com.biz.web.log.trace.DefaultTraceIdServiceImpl;
import com.biz.web.log.trace.TraceIdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 默认存储实现
 *
 * @author francis
 * @since 2024-07-04 16:34
 **/
@Slf4j
public class DefaultTraceStoreService implements TraceStoreService, ApplicationContextAware {

    /**
     * 当前线程追踪id
     */
    private static final ThreadLocal<String> traceIdHolder = new ThreadLocal<>();

    /**
     * 日志追踪Id
     */
    private TraceIdService traceIdService;


    @Override
    public String getTraceId() {
        String traceId = traceIdHolder.get();
        if (Common.isBlank(traceId)) {
            traceIdHolder.set(traceIdService.getId());
            return traceIdHolder.get();
        }
        return traceId;
    }

    @Override
    public void removeTraceId() {
        try {
            traceIdHolder.remove();
        } catch (Exception e) {
            log.error("移除 TraceIdHolder 失败", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            traceIdService = applicationContext.getBean(TraceIdService.class);
        } catch (Exception e) {
            log.error("获取 TraceId Bean 失败", e);
            traceIdService = new DefaultTraceIdServiceImpl();
        }
    }

}
