package com.biz.common.serviceloader;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;

/**
 * 实现了服务加载器的特定逻辑，利用Spring框架的ApplicationContext来加载服务。
 * 此实现类继承自AbstractServiceLoaderProvider，提供了特定于应用程序上下文的服务加载机制。
 *
 * @author francis
 */
@Slf4j
public class ApplicationContextAwareServiceLoaderImpl extends AbstractServiceLoaderProvider {

    /**
     * 加载指定类别的服务实例。
     * 本方法利用BizXBeanUtils.getBean方法从Spring应用程序上下文中获取指定类型的bean实例。
     * 如果找不到对应的bean，将返回null。
     *
     * @param tClass 服务的类别，用于从应用程序上下文中获取对应的bean。
     * @param <T>    服务的类型。
     * @return 类别为tClass的bean实例，如果找不到则返回null。
     */
    @Override
    protected <T> T load(Class<?> tClass) {
        // 输入验证
        if (tClass == null) {
            log.warn("The input class is null, return null instead.");
            return null;
        }

        try {
            // 尝试获取bean实例
            Object bean = BizXBeanUtils.getBean(tClass);
            // 类型安全的转换
            return Common.to(tClass.cast(bean));
        } catch (ClassCastException e) {
            log.error("Failed to cast the bean to the expected type: {}", tClass.getName(), e);
            throw e;

        } catch (Exception e) {
            // 捕获其他潜在异常，例如NoSuchBeanDefinitionException
            log.error("Error loading bean for class: {}", tClass.getName(), e);
            throw e;
        }
    }
}
