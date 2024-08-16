package com.biz.common.application.aware;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 封装了 {@link ApplicationContext} 的基本用法，提供获取 Spring 容器上下文的静态方法。
 *
 * <p>
 * 该类实现了 {@link ApplicationContextAware} 接口，并通过 {@code setApplicationContext}
 * 方法将 {@link ApplicationContext} 实例保存为静态变量，以供全局访问。通过这种方式，
 * 你可以在任何地方获取 Spring 容器上下文，进行依赖注入或获取 Spring 管理的 Bean。
 * </p>
 *
 * <p>
 * 示例用法：
 * </p>
 * <pre>{@code
 * ApplicationContext ctx = ApplicationContextAwareUtils.getApplicationContext();
 * MyBean myBean = ctx.getBean(MyBean.class);
 * }
 * </pre>
 *
 * <p>
 * 该类被标注为 {@link Order}，并指定优先级为最高 {@link Ordered#HIGHEST_PRECEDENCE}，确保它在 Spring 容器初始化过程中优先被加载。
 * </p>
 *
 * <p>
 * 注意：该类是一个工具类，禁止实例化，因此构造方法被私有化。
 * </p>
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public final class ApplicationContextAwareUtils implements ApplicationContextAware {

    /**
     * 静态变量，保存 ApplicationContext 实例。
     */
    @Getter
    private static volatile ApplicationContext applicationContext;

    /**
     * 私有化构造方法，防止实例化。
     */
    private ApplicationContextAwareUtils() {
    }

    /**
     * 设置 ApplicationContext 实例。此方法由 Spring 容器在初始化时自动调用。
     *
     * @param applicationContext 要设置的 ApplicationContext 实例。
     * @throws BeansException 如果在设置 ApplicationContext 时出现错误。
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ApplicationContextAwareUtils.applicationContext != null) {
            log.warn("ApplicationContext已经被设置，此次设置将覆盖之前的实例");
        }
        ApplicationContextAwareUtils.applicationContext = applicationContext;
    }

}
