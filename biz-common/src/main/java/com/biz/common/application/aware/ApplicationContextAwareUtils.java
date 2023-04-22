package com.biz.common.application.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 封装 ApplicationContext 的基本用法
 *
 * @author francis
 */
public class ApplicationContextAwareUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private ApplicationContextAwareUtils() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
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
