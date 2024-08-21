package com.biz.cache.map;


/**
 * 缓存 Map 接口，用于定义缓存的基本操作方法。
 *
 * <p>该接口提供了基本的 {@code put}、{@code get}、{@code remove} 方法，用于存储、获取和删除键值对。
 * 它适用于各种需要缓存数据的场景，如简单的键值对存储。</p>
 *
 * @param <K> 缓存Map的Key类型
 * @param <V> 缓存Map的Value类型
 * @see java.util.concurrent.TimeUnit
 * @since 1.0.1
 * @version 1.0.1
 * @author francis
 */
public interface CacheMap<K, V> {

    /**
     * 设置 Key-Value 值。如果Map中已存在该Key，则不进行存储操作。
     *
     * @param k key，键值对中的键
     * @param v value，键值对中的值
     * @return 返回存储的值，若未存储则返回null
     */
    V put(K k, V v);

    /**
     * 获取指定Key的Value值。
     *
     * @param k key，键值对中的键
     * @return 返回与Key对应的值，若Map中不存在该Key则返回null
     */
    V get(K k);

    /**
     * 删除指定Key的Value值。
     *
     * @param k key，键值对中的键
     * @return 返回删除的Value值，若Map中不存在该Key则返回null
     */
    V remove(K k);

    /**
     * 获取Map中存储的元素个数。
     *
     * @return 返回Map中存储的元素个数
     */
    int size();

}
