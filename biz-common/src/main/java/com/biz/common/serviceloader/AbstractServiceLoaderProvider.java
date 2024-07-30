package com.biz.common.serviceloader;

import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 抽象服务加载器提供者类，优化后的版本。
 * 该类为服务加载器提供了一个模板方法，用于延迟加载服务实现。
 * 延迟加载可以在第一次请求服务时才进行，以提高应用程序的启动性能。
 * 此版本增加了缓存机制，并提供了错误处理的基本框架。
 *
 * @author francis
 */
@Slf4j
public abstract class AbstractServiceLoaderProvider implements ServiceLoaderProvider {

    /**
     * 使用ReentrantLock来保证线程安全地获取服务实例
     */
    private static final ReentrantLock lock = new ReentrantLock(true);

    /**
     * 使用ConcurrentHashMap来缓存已加载的服务实例，以提高性能
     */
    private static final ConcurrentHashMap<Class<?>, Object> serviceCache = new ConcurrentHashMap<>();

    /**
     * 根据类类型获取服务实例。
     * 该方法使用锁来确保并发访问的安全性，确保在多线程环境下不会出现数据竞争的问题。
     * 延迟加载机制由子类实现的load方法提供支持。
     *
     * @param tClass 服务的类类型
     * @param <T>    服务的泛型类型
     * @return 服务实例
     */
    @Override
    public <T> T get(Class<?> tClass) {
        // 尝试从缓存中获取服务实例
        @SuppressWarnings("unchecked")
        T service = (T) serviceCache.get(tClass);
        if (service != null) {
            return service;
        }

        lock.lock();
        try {
            // 如果缓存中不存在，则尝试加载服务实例
            if (serviceCache.containsKey(tClass)) {
                // 已经在尝试加载，避免重复加载，从缓存中获取
                return Common.to(serviceCache.get(tClass));
            }

            service = load(tClass);
            if (service != null) {
                // 成功加载后，放入缓存
                serviceCache.put(tClass, service);
            }
            return service;
        } catch (Exception e) {
            log.error("加载服务实例失败：", e);
            throw e;

        } finally {
            lock.unlock();
        }

    }

    /**
     * 延迟加载服务实现的方法。
     * 该方法由子类实现，用于在第一次请求时加载并返回指定类型的服务实例。
     * 这是一个抽象方法，强制子类提供具体地实现。
     *
     * @param tClass 服务的类类型
     * @param <T>    服务的泛型类型
     * @return 服务实例
     */
    protected abstract <T> T load(Class<?> tClass);

}
