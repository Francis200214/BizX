package com.biz.common.serviceloader;

import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;

import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 该类是抽象服务加载器提供者的具体实现，用于加载指定的服务。
 * 它利用Java的ServiceLoader机制来发现并加载服务实现，并引入了缓存机制以提高性能。
 *
 * <p>通过此类，用户可以便捷地加载服务的实现类，并将其缓存以供后续使用。</p>
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
@Slf4j
public class BizServiceLoader extends AbstractServiceLoaderProvider {

    /**
     * 引入缓存机制，存储已加载的服务。
     */
    private static final ConcurrentHashMap<Class<?>, Object> SERVICE_CACHE = new ConcurrentHashMap<>();

    /**
     * 加载指定类的服务。
     * 该方法是抽象方法的实现，具体加载逻辑委托给了{@link #loadService(Class)}方法。
     *
     * @param tClass 服务的类类型
     * @param <T>    服务的泛型类型
     * @return 加载成功返回服务实例，否则返回null
     */
    @Override
    protected <T> T load(Class<?> tClass) {
        // 检查缓存中是否已加载该服务
        if (SERVICE_CACHE.containsKey(tClass)) {
            return Common.to(SERVICE_CACHE.get(tClass));
        }
        T service = loadService(tClass);
        // 将加载的服务缓存起来（如果非null）
        if (service != null) {
            SERVICE_CACHE.put(tClass, service);
        }
        return service;
    }

    /**
     * 使用ServiceLoader加载指定类的服务。
     * 遍历加载的所有实现类，找到与指定类名匹配的服务并返回。
     * 如果没有找到匹配的服务，则返回null。
     *
     * @param clazz 需要加载的服务的类
     * @param <T>   服务的泛型类型
     * @return 加载成功返回服务实例，否则返回null
     */
    private static <T> T loadService(Class<?> clazz) {
        try {
            ServiceLoader<?> loadedDrivers = ServiceLoader.load(clazz);
            for (Object next : loadedDrivers) {
                if (clazz.getName().equals(next.getClass().getName())) {
                    // 增加类型安全检查
                    if (clazz.isAssignableFrom(next.getClass())) {
                        return Common.to(next);
                    } else {
                        // 记录日志或处理类型不匹配的问题
                        log.warn("类型不匹配：期望 {}，实际 {}", clazz.getName(), next.getClass().getName());
                    }
                }
            }
        } catch (Exception e) {
            log.error("加载服务失败：{}，异常：{}", clazz.getName(), e.getMessage());
            throw e;
        }
        return null;
    }
}
