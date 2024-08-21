package com.biz.cache.map;

import java.util.concurrent.TimeUnit;

/**
 * 定时清除缓存Map接口，扩展了 {@link CacheMap}，支持为每个键值对设置过期时间。
 *
 * <p>该接口在 {@link CacheMap} 基础上增加了设置过期时间的功能，适用于需要自动清除过期数据的缓存场景。</p>
 *
 * @param <K> 缓存Map的Key类型
 * @param <V> 缓存Map的Value类型
 * @see CacheMap
 * @see TimeUnit
 * @see TimedDeletionMap
 * @since 1.0.1
 * @version 1.0.1
 * @author francis
 **/
public interface TimedDeletionCacheMap<K, V> extends CacheMap<K, V> {

    /**
     * 设置 Key-Value 值，并设置过期时间。如果Map中已存在该Key，则不进行存储操作。
     *
     * @param k            key，键值对中的键
     * @param v            value，键值对中的值
     * @param expirationTime 过期时间
     * @param timeUnit     时间单位
     * @return 返回存储的值，若未存储则返回null
     */
    V put(K k, V v, long expirationTime, TimeUnit timeUnit);
}
