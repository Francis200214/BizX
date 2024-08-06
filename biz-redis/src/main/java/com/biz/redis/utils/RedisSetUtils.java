package com.biz.redis.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.SetOperations;

import java.util.Set;

/**
 * Redis Set Utils 提供对Redis集合（Set）数据结构的常用操作方法。
 * 包括获取集合中的所有值、检查值是否存在于集合、添加和移除集合中的值、以及获取集合的长度等。
 *
 * <p>该类依赖于{@link SetOperations}和{@link RedisCommonUtils}。</p>
 *
 * <pre>
 * 示例：
 * RedisSetUtils redisSetUtils = new RedisSetUtils(setOperations, redisCommonUtils);
 * redisSetUtils.sSet("key", value1, value2);
 * </pre>
 *
 * <p>注意：所有操作均假定键和值不为null，且对于可能出现的异常进行了日志记录。</p>
 *
 * @see SetOperations
 * @see RedisCommonUtils
 * @see <a href="https://redis.io/commands#set">Redis Set Commands</a>
 * @author francis
 * @version 1.4.11
 * @since 2024-04-02
 */
@Slf4j
@RequiredArgsConstructor
public class RedisSetUtils {

    private final SetOperations<String, Object> setOperations;
    private final RedisCommonUtils redisCommonUtils;

    /**
     * 根据键获取集合中的所有值。
     *
     * @param key 集合的键，不能为空
     * @return 集合中的所有值
     * @see SetOperations#members(Object)
     */
    public Set<Object> getSetMembers(@NonNull String key) {
        try {
            return setOperations.members(key);
        } catch (Exception e) {
            log.error("根据键获取集合中的所有值时发生异常 error {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据值从一个集合中查询是否存在。
     *
     * @param key   集合的键，不能为空
     * @param value 要查询的值，不能为空
     * @return true表示存在，false表示不存在
     * @see SetOperations#isMember(Object, Object)
     */
    public Boolean isMember(@NonNull String key, @NonNull Object value) {
        try {
            return setOperations.isMember(key, value);
        } catch (Exception e) {
            log.error("根据值从一个集合中查询是否存在时发生异常 error {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 将一个或多个值添加到集合缓存中。
     *
     * @param key    集合的键，不能为空
     * @param values 要添加的值，可以是多个，不能为空
     * @return 成功添加的个数
     * @see SetOperations#add(Object, Object...)
     */
    public Long addToSet(@NonNull String key, @NonNull Object... values) {
        try {
            return setOperations.add(key, values);
        } catch (Exception e) {
            log.error("将一个或多个值添加到集合缓存时发生异常 error {}", e.getMessage(), e);
        }
        return 0L;
    }

    /**
     * 将一个或多个值添加到集合缓存中，并设置过期时间。
     *
     * @param key    集合的键，不能为空
     * @param time   过期时间（秒）
     * @param values 要添加的值，可以是多个，不能为空
     * @return 成功添加的个数
     * @see SetOperations#add(Object, Object...)
     */
    public Long addToSetWithExpire(@NonNull String key, long time, @NonNull Object... values) {
        try {
            Long count = setOperations.add(key, values);
            redisCommonUtils.setExpire(key, time);
            return count;
        } catch (Exception e) {
            log.error("将一个或多个值添加到集合缓存并设置过期时间时发生异常 error {}", e.getMessage(), e);
        }
        return 0L;
    }

    /**
     * 获取集合缓存的长度。
     *
     * @param key 集合的键，不能为空
     * @return 集合的长度
     * @see SetOperations#size(Object)
     */
    public Long getSetSize(@NonNull String key) {
        try {
            return setOperations.size(key);
        } catch (Exception e) {
            log.error("获取集合缓存的长度时发生异常 error {}", e.getMessage(), e);
        }
        return 0L;
    }

    /**
     * 移除集合中一个或多个值。
     *
     * @param key    集合的键，不能为空
     * @param values 要移除的值，可以是多个，不能为空
     * @return 成功移除的个数
     * @see SetOperations#remove(Object, Object...)
     */
    public Long removeFromSet(@NonNull String key, @NonNull Object... values) {
        try {
            return setOperations.remove(key, values);
        } catch (Exception e) {
            log.error("移除集合中一个或多个值时发生异常 error {}", e.getMessage(), e);
        }
        return 0L;
    }
}
