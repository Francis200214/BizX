package com.biz.web.interceptor;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.web.log.store.DefaultTraceStoreService;
import com.biz.web.log.store.TraceStoreService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志追踪
 *
 * @author francis
 * @since 2024-07-04 17:11
 **/
@Slf4j
public class TraceInterceptor implements HandlerInterceptor, Ordered {

    private TraceStoreService traceStoreService;

    public TraceInterceptor() {
        try {
            this.traceStoreService = BizXBeanUtils.getBean(TraceStoreService.class);
        } catch (Exception e) {
            log.warn("TraceStoreService not found, use DefaultTraceStoreService");
            this.traceStoreService = new DefaultTraceStoreService();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("traceId", traceStoreService.getTraceId());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        MDC.remove("traceId");
        traceStoreService.removeTraceId();
    }

    @Override
    public int getOrder() {
        return 99;
    }


}
