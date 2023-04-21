package com.biz.common.bean;

import com.biz.common.application.aware.ApplicationContextAwareUtils;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Bean Util
 * 对Bean的一些操作
 *
 * @author francis
 * @create: 2023-04-09 22:16
 **/
public final class BizXBeanUtils {

    /**
     * 获取 Bean
     *
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 获取存在 X 注解的 Bean
     *
     * @param clazz X 注解
     * @return
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clazz) {
        return getApplicationContext().getBeansWithAnnotation(clazz);
    }

    /**
     * 获取所有的 Bean Class
     *
     * @return
     */
    public static List<Class<?>> getBeanDefinitionClasses() {
        List<Class<?>> classList = new ArrayList<>();
        for (String beanName : getApplicationContext().getBeanDefinitionNames()) {
            classList.add(getApplicationContext().getBean(beanName).getClass());
        }
        return classList;
    }

    /**
     * 获取所有的 Bean
     *
     * @return 所有的 Bean
     */
    public static String[] getBeanDefinitionNames() {
        return getApplicationContext().getBeanDefinitionNames();
    }


    /**
     * 获取 ApplicationContext
     *
     * @return ApplicationContext
     */
    private static ApplicationContext getApplicationContext() {
        return ApplicationContextAwareUtils.getApplicationContext();
    }


}
