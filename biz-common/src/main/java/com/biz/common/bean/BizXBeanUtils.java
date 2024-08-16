package com.biz.common.bean;

import com.biz.common.application.aware.ApplicationContextAwareUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Bean Util工具类
 * </p>
 * <p>
 * 提供对 Spring Bean 的一些操作工具方法。该类包含获取 Bean 实例、获取带注解的 Bean、获取实现特定类型的 Bean、获取所有 Bean 类等功能。
 * </p>
 * <p>
 * 注意：该工具类假设 {@link ApplicationContextAwareUtils} 提供了有效的 {@link ApplicationContext}。
 * </p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 *     MyBean myBean = BizXBeanUtils.getBean(MyBean.class);
 * }
 * </pre>
 *
 * @see ApplicationContextAwareUtils
 * @see ApplicationContext
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
public final class BizXBeanUtils {

    /**
     * 私有构造函数，工具类不应被实例化。
     */
    private BizXBeanUtils() {
        // 工具类不应被实例化
    }

    /**
     * 获取指定类型的 Bean 实例。
     *
     * @param clazz 要获取的 Bean 的类型，不能为空
     * @param <T>   Bean 的类型
     * @return 指定类型的 Bean 实例
     * @throws BeansException 如果没有找到该类型的 Bean 或 Bean 初始化失败
     * @see ApplicationContext#getBean(Class)
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 获取指定名称的 Bean 实例。
     *
     * @param clazz 要获取的 Bean 的名称，不能为空
     * @return 指定名称的 Bean 实例
     * @throws BeansException 如果没有找到该名称的 Bean 或 Bean 初始化失败
     * @see ApplicationContext#getBean(String)
     */
    public static Object getBean(String clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 获取带有指定注解的所有 Bean。
     *
     * @param clazz 注解类型，不能为空
     * @return 带有指定注解的所有 Bean 的名称和实例映射
     * @see ApplicationContext#getBeansWithAnnotation(Class)
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clazz) {
        return getApplicationContext().getBeansWithAnnotation(clazz);
    }

    /**
     * 获取指定类型的所有 Bean，包括实现该接口或继承该类的所有 Bean。
     *
     * @param type Bean 的类型，可以为 null
     * @param <T>  Bean 的类型
     * @return 指定类型的所有 Bean 的名称和实例映射
     * @throws BeansException 如果获取 Bean 失败
     * @see ApplicationContext#getBeansOfType(Class)
     */
    public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException {
        return getApplicationContext().getBeansOfType(type);
    }

    /**
     * 获取所有 Bean 的 Class 类型。
     *
     * @return 所有 Bean 的 Class 类型列表
     * @see ApplicationContext#getBeanDefinitionNames()
     */
    public static List<Class<?>> getBeanDefinitionClasses() {
        List<Class<?>> classList = new ArrayList<>();
        for (String beanName : getApplicationContext().getBeanDefinitionNames()) {
            classList.add(getApplicationContext().getBean(beanName).getClass());
        }
        return classList;
    }

    /**
     * 获取所有 Bean 的名称。
     *
     * @return 所有 Bean 的名称数组
     * @see ApplicationContext#getBeanDefinitionNames()
     */
    public static String[] getBeanDefinitionNames() {
        return getApplicationContext().getBeanDefinitionNames();
    }

    /**
     * 获取 ApplicationContext 实例。
     *
     * @return ApplicationContext 实例
     * @see ApplicationContextAwareUtils#getApplicationContext()
     */
    public static ApplicationContext getApplicationContext() {
        return ApplicationContextAwareUtils.getApplicationContext();
    }
}
