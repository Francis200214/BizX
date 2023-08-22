package com.biz.common.application.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


/**
 * 封装 ApplicationContext 的基本用法
 *
 * @author francis
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
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
