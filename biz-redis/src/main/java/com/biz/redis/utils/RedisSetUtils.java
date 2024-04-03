package com.biz.redis.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Redis Set Utils 有序集合
 *
 * @author francis
 * @create 2024-04-02 17:17
 **/
@Slf4j
@Component
public class RedisSetUtils {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisCommonUtils redisCommonUtils;

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(@NonNull String key) {
        try {
            return redisTemplate.opsForSet().members(key);

        } catch (Exception e) {
            log.error("根据key获取Set中的所有值 出现异常 error {}", e.getMessage(), e);
        }

        return null;
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean sHasKey(@NonNull String key, @NonNull Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);

        } catch (Exception e) {
            log.error("根据value从一个set中查询,是否存在 出现异常 error {}", e.getMessage(), e);
        }

        return false;
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSet(@NonNull String key, @NonNull Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("将数据放入set缓存 出现异常 error {}", e.getMessage(), e);
        }

        return 0L;
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSetAndTime(@NonNull String key, long time, @NonNull Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            redisCommonUtils.expire(key, time);
            return count;

        } catch (Exception e) {
            log.error("将set数据放入缓存 出现异常 error {}", e.getMessage(), e);
        }

        return 0L;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public Long sGetSetSize(@NonNull String key) {
        try {
            return redisTemplate.opsForSet().size(key);

        } catch (Exception e) {
            log.error("获取set缓存的长度 出现异常 error {}", e.getMessage(), e);

        }

        return 0L;
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long setRemove(@NonNull String key, @NonNull Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);

        } catch (Exception e) {
            log.error("移除值为value的 出现异常 error {}", e.getMessage(), e);
        }

        return 0L;
    }

}
