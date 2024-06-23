package com.biz.redis.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Redis Map Utils
 *
 * @author francis
 * @create 2024-04-02 17:16
 **/
@Slf4j
@RequiredArgsConstructor
public class RedisMapUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisCommonUtils redisCommonUtils;


    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(@NonNull String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往某一个 key 中插入多条数据
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(@NonNull String key, @NonNull Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;

        } catch (Exception e) {
            log.error("error {}", e.getMessage(), e);
        }

        return false;
    }

    /**
     * hash 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(@NonNull String key, @NonNull Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            redisCommonUtils.expire(key, time);
            return true;

        } catch (Exception e) {
            log.error("HashSet设置时间 出现异常 error {}", e.getMessage(), e);
        }

        return false;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(@NonNull String key, @NonNull String item, @NonNull Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;

        } catch (Exception e) {
            log.error("向一张hash表中放入数据,如果不存在将创建 出现异常 error {}", e.getMessage(), e);
        }

        return false;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(@NonNull String key, @NonNull String item, @NonNull Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            redisCommonUtils.expire(key, time);
            return true;

        } catch (Exception e) {
            log.error("向一张hash表中放入数据,如果不存在将创建 出现异常 error {}", e.getMessage(), e);
        }

        return false;
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(@NonNull String key, @NonNull Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(@NonNull String key, @NonNull String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(@NonNull String key, @NonNull String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

}
