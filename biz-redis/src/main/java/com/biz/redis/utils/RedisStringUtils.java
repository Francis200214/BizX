package com.biz.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis String Utils
 *
 * @author francis
 * @create 2024-04-02 17:16
 **/
@Slf4j
@Component
public class RedisStringUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisCommonUtils redisCommonUtils;


    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;

        } catch (Exception e) {
            log.error("普通缓存放入 error {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("普通缓存放入并设置时间 出现异常 error {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta <= 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        if (key == null) {
            throw new RuntimeException("key不能为空");
        }
        if (!redisCommonUtils.hasKey(key)) {
            throw new RuntimeException("key不存在");
        }

        try {
            return redisTemplate.opsForValue().increment(key, delta);

        } catch (Exception e) {
            log.error("递增 error {}", e.getMessage(), e);
            return -1;
        }

    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        if (key == null) {
            throw new RuntimeException("key不能为空");
        }

        return redisTemplate.opsForValue().increment(key, -delta);
    }


}
