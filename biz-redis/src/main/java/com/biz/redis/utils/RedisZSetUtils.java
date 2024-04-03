package com.biz.redis.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * Redis ZSet Utils 无序集合
 *
 * @author francis
 * @create 2024-04-03 08:48
 **/
@Slf4j
@Component
public class RedisZSetUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static RedisTemplate<String, Object> redisTem;

    @PostConstruct
    public void initRedisTem() {
        redisTem = redisTemplate;
    }

    /**
     * 存储有序集合
     *
     * @param key   键
     * @param value 值
     * @param score 排序
     */
    public static void zSet(@NonNull String key, @NonNull Object value, double score) {
        redisTem.opsForZSet().add(key, value, score);
    }

    /**
     * 存储值
     *
     * @param key 键
     * @param set 集合
     */
    public static void zSet(@NonNull String key, @NonNull Set set) {
        redisTem.opsForZSet().add(key, set);
    }

    /**
     * 获取key指定范围的值
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @return 返回set
     */
    public static Set<Object> zGet(@NonNull String key, long start, long end) {
        return redisTem.opsForZSet().range(key, start, end);
    }

    /**
     * 获取key对应的所有值
     *
     * @param key 键
     * @return 返回set
     */
    public static Set<Object> zGet(@NonNull String key) {
        return redisTem.opsForZSet().range(key, 0, -1);
    }

    /**
     * 获取对用数据的大小
     *
     * @param key 键
     * @return 键值大小
     */
    public static Long zGetSize(@NonNull String key) {
        return redisTem.opsForZSet().size(key);
    }

}
