package com.biz.common.singleton;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 单例管理器类，用于懒汉式地管理单例对象。
 * 该类支持延迟加载功能，确保单例对象的唯一性和可控性。
 *
 * <p>此类提供了一种灵活且线程安全的单例管理机制，可以在应用程序中使用，
 * 以确保单例对象的惰性初始化。</p>
 *
 * <pre>{@code
 * // 示例用法
 * Singleton<UserService> userServiceSingleton = Singleton.createWithSupplier(UserService::new);
 * UserService userService = userServiceSingleton.get();
 * }</pre>
 *
 * @param <T> 单例对象的类型
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
public final class Singleton<T> {

    private final Supplier<T> supplier;
    private volatile T instance;

    /**
     * 构造函数，私有化以防止外部实例化。
     *
     * @param supplier 单例对象的供应商，用于延迟加载单例对象。
     *                 必须不为空，否则抛出NullPointerException。
     */
    public Singleton(Supplier<T> supplier) {
        this.supplier = Objects.requireNonNull(supplier, "Supplier cannot be null");
    }

    /**
     * 静态工厂方法，用于设置并返回单例对象的供应商。
     * 这种方式提供了更灵活的单例对象创建方式，同时也隐藏了单例对象的创建细节。
     *
     * @param supplier 单例对象的供应商。
     * @param <T>      单例对象的类型。
     * @return Singleton实例。
     */
    public static <T> Singleton<T> createWithSupplier(Supplier<T> supplier) {
        return new Singleton<>(supplier);
    }

    /**
     * 获取单例对象。
     * 如果单例对象尚未加载，则通过供应商加载并返回；否则直接返回已加载的实例。
     * 采用经典的双重检查锁定方式实现线程安全的懒汉式加载。
     *
     * @return 单例对象。
     */
    public T get() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = supplier.get();
                }
            }
        }
        return instance;
    }
}
