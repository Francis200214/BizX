package com.biz.redis.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * Redis ZSet Utils 无序集合
 *
 * @author francis
 * @create 2024-04-03 08:48
 **/
@Slf4j
@RequiredArgsConstructor
public class RedisZSetUtils {


    private final ZSetOperations<String, Object> zSetOperations;


    /**
     * 存储有序集合
     *
     * @param key   键
     * @param value 值
     * @param score 排序
     */
    public void zSet(@NonNull String key, @NonNull Object value, double score) {
        zSetOperations.add(key, value, score);
    }

    /**
     * 存储值
     *
     * @param key 键
     * @param set 集合
     */
    public void zSet(@NonNull String key, @NonNull Set set) {
        zSetOperations.add(key, set);
    }

    /**
     * 获取key指定范围的值
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @return 返回set
     */
    public Set<Object> zGet(@NonNull String key, long start, long end) {
        return zSetOperations.range(key, start, end);
    }

    /**
     * 获取key对应的所有值
     *
     * @param key 键
     * @return 返回set
     */
    public Set<Object> zGet(@NonNull String key) {
        return zSetOperations.range(key, 0, -1);
    }

    /**
     * 获取对用数据的大小
     *
     * @param key 键
     * @return 键值大小
     */
    public Long zGetSize(@NonNull String key) {
        return zSetOperations.size(key);
    }

}
