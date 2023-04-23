package com.biz.map;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 缓存 Map 抽象类
 *
 * @author francis
 * @create: 2023-04-23 17:27
 **/
public abstract class AbstractCatchMap<K, V> implements CatchMap<K, V> {

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
