package com.biz.redis.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * Redis String Utils
 *
 * @author francis
 * @create 2024-04-02 17:16
 **/
@Slf4j
@RequiredArgsConstructor
public class RedisStringUtils {

    private final ValueOperations<String, Object> valueOperations;

    private final RedisCommonUtils redisCommonUtils;


    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(@NonNull String key) {
        return valueOperations.get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(@NonNull String key, @NonNull Object value) {
        try {
            valueOperations.set(key, value);
            return true;

        } catch (Exception e) {
            log.error("普通缓存放入 error {}", e.getMessage(), e);
        }

        return false;
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(@NonNull String key, @NonNull Object value, long time) {
        try {
            if (time > 0) {
                valueOperations.set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;

        } catch (Exception e) {
            log.error("普通缓存放入并设置时间 出现异常 error {}", e.getMessage(), e);
        }

        return false;
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return 返回 Null 则表示 Value 不是数字类型
     */
    public Long incr(@NonNull String key, long delta) {
        if (delta <= 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        if (!redisCommonUtils.hasKey(key)) {
            throw new RuntimeException("key不存在");
        }

        try {
            return valueOperations.increment(key, delta);

        } catch (Exception e) {
            log.error("递增 error {}", e.getMessage(), e);
        }

        return null;
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public Long decr(@NonNull String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }

        if (!redisCommonUtils.hasKey(key)) {
            throw new RuntimeException("key不存在");
        }

        try {
            return valueOperations.decrement(key, delta);

        } catch (Exception e) {
            log.error("递减 error {}", e.getMessage(), e);
        }

        return null;
    }


}
