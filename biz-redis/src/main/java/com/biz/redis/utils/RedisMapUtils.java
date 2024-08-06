package com.biz.redis.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;

import java.util.Map;

/**
 * Redis Map Utils
 * <p>提供对Redis哈希（Hash）数据结构的常用操作方法，包括获取和设置哈希值、删除哈希项、判断哈希项是否存在以及哈希值的递增和递减操作等。</p>
 *
 * <p>该类依赖于{@link HashOperations}和{@link RedisCommonUtils}。</p>
 *
 * <pre>
 * 示例：
 * RedisMapUtils redisMapUtils = new RedisMapUtils(hashOperations, redisCommonUtils);
 * redisMapUtils.hset("key", "item", value);
 * </pre>
 *
 * <p>注意：所有操作均假定键和项不为null，且对于可能出现的异常进行了日志记录。</p>
 *
 * @see HashOperations
 * @see RedisCommonUtils
 * @see <a href="https://redis.io/commands#hash">Redis Hash Commands</a>
 * @author francis
 * @version 1.4.11
 * @since 2024-04-02
 */
@Slf4j
@RequiredArgsConstructor
public class RedisMapUtils {

    private final HashOperations<String, String, Object> hashOperations;
    private final RedisCommonUtils redisCommonUtils;

    /**
     * 获取哈希表中的某个值。
     *
     * @param key  哈希表的键，不能为空
     * @param item 哈希表项的键，不能为空
     * @return 哈希表中的值
     * @see HashOperations#get(Object, Object)
     */
    public Object hget(@NonNull String key, @NonNull String item) {
        return hashOperations.get(key, item);
    }

    /**
     * 获取哈希表中所有的键值对。
     *
     * @param key 哈希表的键，不能为空
     * @return 哈希表中所有的键值对
     * @see HashOperations#entries(Object)
     */
    public Map<String, Object> hmget(@NonNull String key) {
        return hashOperations.entries(key);
    }

    /**
     * 向哈希表中添加多个键值对。
     *
     * @param key 哈希表的键，不能为空
     * @param map 键值对映射，不能为空
     * @return true表示成功，false表示失败
     * @see HashOperations#putAll(Object, Map)
     */
    public boolean hmset(@NonNull String key, @NonNull Map<String, Object> map) {
        try {
            hashOperations.putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("向哈希表中添加多个键值对时发生异常 error {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 向哈希表中添加多个键值对，并设置过期时间。
     *
     * @param key  哈希表的键，不能为空
     * @param map  键值对映射，不能为空
     * @param time 过期时间（秒）
     * @return true表示成功，false表示失败
     * @see HashOperations#putAll(Object, Map)
     */
    public boolean hmset(@NonNull String key, @NonNull Map<String, Object> map, long time) {
        try {
            hashOperations.putAll(key, map);
            redisCommonUtils.setExpire(key, time);
            return true;
        } catch (Exception e) {
            log.error("向哈希表中添加多个键值对并设置过期时间时发生异常 error {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 向哈希表中添加一个键值对，如果不存在将创建。
     *
     * @param key   哈希表的键，不能为空
     * @param item  哈希表项的键，不能为空
     * @param value 哈希表中的值，不能为空
     * @return true表示成功，false表示失败
     * @see HashOperations#put(Object, Object, Object)
     */
    public boolean hset(@NonNull String key, @NonNull String item, @NonNull Object value) {
        try {
            hashOperations.put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("向哈希表中添加一个键值对时发生异常 error {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 向哈希表中添加一个键值对，并设置过期时间。
     *
     * @param key   哈希表的键，不能为空
     * @param item  哈希表项的键，不能为空
     * @param value 哈希表中的值，不能为空
     * @param time  过期时间（秒）
     * @return true表示成功，false表示失败
     * @see HashOperations#put(Object, Object, Object)
     */
    public boolean hset(@NonNull String key, @NonNull String item, @NonNull Object value, long time) {
        try {
            hashOperations.put(key, item, value);
            redisCommonUtils.setExpire(key, time);
            return true;
        } catch (Exception e) {
            log.error("向哈希表中添加一个键值对并设置过期时间时发生异常 error {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 删除哈希表中的值。
     *
     * @param key  哈希表的键，不能为空
     * @param item 哈希表项的键，可以为多个，不能为空
     * @see HashOperations#delete(Object, Object...)
     */
    public void delete(@NonNull String key, @NonNull Object... item) {
        hashOperations.delete(key, item);
    }

    /**
     * 判断哈希表中是否存在某个项的值。
     *
     * @param key  哈希表的键，不能为空
     * @param item 哈希表项的键，不能为空
     * @return true表示存在，false表示不存在
     * @see HashOperations#hasKey(Object, Object)
     */
    public boolean hasKey(@NonNull String key, @NonNull String item) {
        return hashOperations.hasKey(key, item);
    }

    /**
     * 哈希值递增操作，如果不存在，将创建一个新的哈希项。
     *
     * @param key  哈希表的键，不能为空
     * @param item 哈希表项的键，不能为空
     * @param by   增加的值，必须大于0
     * @return 增加后的值
     * @see HashOperations#increment(Object, Object, double)
     */
    public double increment(@NonNull String key, @NonNull String item, double by) {
        return hashOperations.increment(key, item, by);
    }

    /**
     * 哈希值递减操作。
     *
     * @param key  哈希表的键，不能为空
     * @param item 哈希表项的键，不能为空
     * @param by   减少的值，必须小于0
     * @return 减少后的值
     * @see HashOperations#increment(Object, Object, double)
     */
    public double decrement(@NonNull String key, @NonNull String item, double by) {
        return hashOperations.increment(key, item, -by);
    }

}
