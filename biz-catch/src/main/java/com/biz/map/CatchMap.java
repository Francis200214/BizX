package com.biz.map;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 缓存 Map 接口
 *
 * @author francis
 * @create: 2023-04-23 17:20
 **/
public interface CatchMap<K, V> {

    /**
     * 设置 Key-Value 值
     *
     * @param k key
     * @param v value
     * @return
     */
    V put(K k, V v);

    /**
     * 设置 Key-Value 值，并设置过期时间
     *
     * @param k    key
     * @param v    value
     * @param died 过期时间
     * @return
     */
    V put(K k, V v, long died);

    /**
     * 获取 value 值
     * 当 Key 不存在时执行 自定义函数
     *
     * @param k        key
     * @param supplier 自定义函数
     * @return 值
     */
    V get(K k, Supplier<V> supplier);

    /**
     * 获取 value 值
     *
     * @param k key
     * @return 值
     */
    V get(K k);

    /**
     * 获取 value 值
     * 当 Key 不存在时执行 自定义函数
     *
     * @param k        key
     * @param function 自定义函数
     * @return 值
     */
    V get(K k, Function<K, V> function);

}
