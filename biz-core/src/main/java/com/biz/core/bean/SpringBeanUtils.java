package com.biz.core.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.inject.Named;

/**
 * 获取spring容器中的bean的工具类
 *
 * @author francis
 */
@Named
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private SpringBeanUtils() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
