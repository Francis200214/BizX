package com.biz.redis.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Redis常用工具类，提供缓存操作的通用方法。
 * 包括设置缓存失效时间、获取缓存剩余时间、判断缓存是否存在及删除缓存等功能。
 *
 * <p>该类依赖于{@link RedisTemplate}提供的各种Redis操作。</p>
 *
 * @author francis
 * @version 1.4.11
 * @since 2024-04-02
 * @see RedisTemplate
 * @see TimeUnit
 */
@Slf4j
@RequiredArgsConstructor
public class RedisCommonUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置指定缓存的失效时间。
     *
     * @param key  缓存键
     * @param time 失效时间（秒），必须大于0
     * @throws IllegalArgumentException 如果时间小于等于0
     * @see RedisTemplate#expire(Object, long, TimeUnit)
     */
    public void setExpire(@NonNull String key, long time) {
        if (time <= 0) {
            throw new IllegalArgumentException("失效时间必须大于0");
        }
        try {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("设置缓存失效时间时发生异常 error {}", e.getMessage(), e);
        }
    }

    /**
     * 获取指定缓存的剩余存活时间。
     *
     * @param key 缓存键，不能为空
     * @return 剩余时间（秒），返回0表示永久有效，返回-1表示无此键
     * @see RedisTemplate#getExpire(Object, TimeUnit)
     */
    public long getRemainingTime(@NonNull String key) {
        return Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS))
                .orElse(-1L);
    }

    /**
     * 判断指定缓存键是否存在。
     *
     * @param key 缓存键，不能为空
     * @return true 表示存在，false 表示不存在
     * @see RedisTemplate#hasKey(Object)
     */
    public boolean exists(@NonNull String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            log.error("判断缓存键是否存在时发生异常 error {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 删除缓存。
     *
     * @param keys 缓存键，可以传入一个或多个
     * @see RedisTemplate#delete(Object)
     * @see RedisTemplate#delete(java.util.Collection)
     */
    public void delete(@NonNull String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(Arrays.asList(keys));
            }
        }
    }
}
