package com.biz.common.serviceloader;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 抽象服务加载器提供者类，优化后的版本。
 * 该类为服务加载器提供了一个模板方法，用于延迟加载服务实现。
 * 延迟加载可以在第一次请求服务时才进行，以提高应用程序的启动性能。
 * 此版本增加了缓存机制，并提供了错误处理的基本框架。
 *
 * <p>该类通过缓存机制和锁机制确保了在多线程环境下的安全性和高效性。
 * 子类需要实现抽象的{@link #load(Class)}方法，以便在首次访问时加载服务实例。</p>
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
@Slf4j
public abstract class AbstractServiceLoaderProvider implements ServiceLoaderProvider {

    /**
     * 使用ReentrantLock来保证线程安全地获取服务实例。
     */
    private static final ReentrantLock lock = new ReentrantLock(true);

    /**
     * 使用ConcurrentHashMap来缓存已加载的服务实例，以提高性能。
     */
    private static final ConcurrentHashMap<Class<?>, Object> SERVICE_CACHE = new ConcurrentHashMap<>();

    /**
     * 根据类类型获取服务实例。
     * 该方法使用锁来确保并发访问的安全性，确保在多线程环境下不会出现数据竞争的问题。
     * 延迟加载机制由子类实现的{@link #load(Class)}方法提供支持。
     *
     * @param tClass 服务的类类型
     * @param <T>    服务的泛型类型
     * @return 服务实例，如果无法加载则可能返回null
     */
    @Override
    public <T> T get(Class<?> tClass) {
        T service = (T) SERVICE_CACHE.get(tClass);
        if (service != null) {
            return service;
        }

        lock.lock();
        try {
            // 双重检查锁定，避免重复加载
            service = (T) SERVICE_CACHE.get(tClass);
            if (service == null) {
                service = load(tClass);
                if (service != null) {
                    SERVICE_CACHE.put(tClass, service);
                    log.info("成功加载服务实例：{}", tClass.getName());
                }
            }
        } catch (Exception e) {
            log.error("加载服务实例失败：", e);
            throw e;
        } finally {
            lock.unlock();
        }

        return service;
    }


    /**
     * 延迟加载服务实现的方法。
     * 该方法由子类实现，用于在第一次请求时加载并返回指定类型的服务实例。
     * 这是一个抽象方法，强制子类提供具体的实现。
     *
     * @param tClass 服务的类类型
     * @param <T>    服务的泛型类型
     * @return 服务实例
     */
    @SuppressWarnings("unchecked")
    protected abstract <T> T load(Class<?> tClass);

}
