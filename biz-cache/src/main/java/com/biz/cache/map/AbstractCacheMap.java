package com.biz.cache.map;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 缓存 Map 抽象类
 *1.0.12023-04-23 17:27
 **/
public abstract class AbstractCacheMap<K, V> implements CacheMap<K, V> {

    @Override
    public V put(K k, V v) {
        return null;
    }

    @Override
    public V put(K k, V v, long died) {
        return null;
    }

    @Override
    public V get(K k, Supplier<V> supplier) {
        return null;
    }

    @Override
    public V get(K k) {
        return null;
    }

    @Override
    public V get(K k, Function<K, V> function) {
        return null;
    }

}
