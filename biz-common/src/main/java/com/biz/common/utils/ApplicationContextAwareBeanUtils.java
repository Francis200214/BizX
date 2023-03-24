package com.biz.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 获取spring容器中的bean的工具类
 *
 * @author francis
 */
public class ApplicationContextAwareBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private ApplicationContextAwareBeanUtils() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextAwareBeanUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
