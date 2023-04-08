package com.biz.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

    /**
     * 获取 Bean
     *
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取存在 X 注解的 Bean
     *
     * @param clazz X 注解
     * @return
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clazz) {
        return applicationContext.getBeansWithAnnotation(clazz);
    }

    /**
     * 获取所有的 Bean Class
     *
     * @return
     */
    public static List<Class<?>> getBeanDefinitionClasses() {
        List<Class<?>> classList = new ArrayList<>();
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            classList.add(applicationContext.getBean(beanName).getClass());
        }
        return classList;
    }

    /**
     * 获取所有的 Bean
     *
     * @return 所有的 Bean
     */
    public static String[] getBeanDefinitionNames() {
        return applicationContext.getBeanDefinitionNames();
    }

}
