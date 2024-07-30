package com.biz.common.singleton;

import java.util.function.Supplier;

/**
 * 单例管理器类，用于懒汉式地管理单例对象。
 * 该类支持延迟加载和重置单例对象的功能，确保单例对象的唯一性和可控性。
 *
 * @param <T> 单例对象的类型
 * @author francis
 */
public final class Singleton<T> {

    private final Supplier<T> supplier;
    private T instance;
    private volatile boolean loaded = false;

    /**
     * 构造函数，私有化以防止外部实例化。
     *
     * @param supplier 单例对象的供应商，用于延迟加载单例对象。
     *                 必须不为空，否则抛出NullPointerException。
     */
    public Singleton(Supplier<T> supplier) {
        if (supplier == null) {
            throw new NullPointerException("Supplier not null");
        }
        this.supplier = supplier;
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
     * 采用双重检查锁定的方式实现线程安全的懒汉式加载。
     *
     * @return 单例对象。
     */
    public T get() {
        if (!loaded) {
            synchronized (this) {
                if (instance == null) {
                    instance = supplier.get();
                }
                loaded = true;
            }
        }
        return instance;
    }

    /**
     * 重置单例对象的加载状态，使得下次调用get()方法时重新加载单例对象。
     * 这个方法在需要重新初始化单例对象的场景下非常有用，比如在测试或者需要刷新单例对象的情况下。
     */
    public void reset() {
        loaded = false;
    }

}
