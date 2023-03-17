package com.biz.common.singleton;


import java.util.function.Supplier;


/**
 * 单例管理器
 *
 * @author francis
 */
public final class Singleton<T> {

    private final Supplier<T> supplier;
    private T instance;
    private volatile boolean loaded = false;

    public Singleton(Supplier<T> supplier) {
        if (supplier == null) {
            throw new NullPointerException("Supplier not null");
        }
        this.supplier = supplier;
    }

    public static <T> Singleton<T> setSupplier(Supplier<T> supplier) {
        return new Singleton<>(supplier);
    }

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
     * 重置单例
     */
    public void reset() {
        loaded = false;
    }

}
