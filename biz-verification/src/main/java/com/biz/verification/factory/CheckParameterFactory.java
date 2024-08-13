package com.biz.verification.factory;

import com.biz.verification.strategy.CheckParameterStrategy;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * 检查参数工厂。
 * <p>该工厂类在注册 Bean 后，将所有实现 {@link CheckParameterStrategy} 接口的实现类加入到一个 Map 中，方便后续使用。</p>
 *
 * <p>该类实现了 {@link InitializingBean} 接口，并在 {@link #afterPropertiesSet()} 方法中进行初始化操作。</p>
 *
 * @see CheckParameterStrategy
 * @see InitializingBean
 * @author francis
 * @version 1.0
 * @since 2023-04-08
 **/
@Slf4j
public class CheckParameterFactory implements InitializingBean, ApplicationContextAware {

    /**
     * 缓存校验策略的 Map，key 为 {@link Annotation}注解类型，value 为对应的校验策略 {@link CheckParameterStrategy} 实现类。
     */
    private static final Map<Class<?>, CheckParameterStrategy> CHECK_PARAMETER_STRATEGY_MAP = new HashMap<>();

    private static ApplicationContext applicationContext;

    /**
     * 在 Bean 属性设置后进行初始化，将所有实现 {@link CheckParameterStrategy} 接口的实现类注册到 {@link #CHECK_PARAMETER_STRATEGY_MAP} 中。
     */
    @Override
    public void afterPropertiesSet() {
        for (CheckParameterStrategy handler : applicationContext.getBeansOfType(CheckParameterStrategy.class).values()) {
            CHECK_PARAMETER_STRATEGY_MAP.put(handler.getCheckAnnotation(), handler);
        }
    }

    /**
     * 处理数据，校验接口中的参数。
     *
     * @param annotation 参数上的注解，不能为空
     * @param byFieldValue       参数值
     * @param className 类名
     * @param methodName 方法名
     * @param fieldName 参数名称
     * @throws Throwable 如果校验失败或处理过程中出现异常
     */
    public void handle(@NotNull Annotation annotation, Object byFieldValue, String className, String methodName, String fieldName) throws Throwable {
        // 从缓存 Map 中获取对应的处理实现
        CheckParameterStrategy checkParameterStrategy = CHECK_PARAMETER_STRATEGY_MAP.get(annotation.annotationType());
        if (checkParameterStrategy == null) {
            log.warn("未找到对应的校验策略，注解类型：{}", annotation.annotationType());
            return;
        }
        // 处理具体类型的参数
        checkParameterStrategy.check(annotation, byFieldValue, className, methodName, fieldName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CheckParameterFactory.applicationContext = applicationContext;
    }
}
