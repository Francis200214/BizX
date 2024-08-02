package com.biz.common.application.aware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


/**
 * 封装 ApplicationContext 的基本用法
 *
 * @author francis
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationContextAwareUtils implements ApplicationContextAware {

    private static volatile ApplicationContext applicationContext;

    private ApplicationContextAwareUtils() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ApplicationContextAwareUtils.applicationContext != null) {
            log.warn("ApplicationContext已经被设置，此次设置将覆盖之前的实例");
        }
        ApplicationContextAwareUtils.applicationContext = applicationContext;
    }

    /**
     * 获取 ApplicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


}
