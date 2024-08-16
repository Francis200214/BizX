package com.biz.redis.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * Redis String Utils 提供对Redis字符串（String）数据结构的常用操作方法。
 * 包括获取和设置字符串值、设置带过期时间的值、以及对值进行递增和递减操作等。
 *
 * <p>该类依赖于{@link ValueOperations}和{@link RedisCommonUtils}。</p>
 *
 * <pre>
 * 示例：
 * RedisStringUtils redisStringUtils = new RedisStringUtils(valueOperations, redisCommonUtils);
 * redisStringUtils.set("key", value);
 * </pre>
 *
 * <p>注意：所有操作均假定键和值不为null，且对于可能出现的异常进行了日志记录。</p>
 *
 * @see ValueOperations
 * @see RedisCommonUtils
 * @see <a href="https://redis.io/commands#string">Redis String Commands</a>
 * @author francis
 * @version 1.4.11
 * @since 1.0.1
 */
@Slf4j
@RequiredArgsConstructor
public class RedisStringUtils {

    private final ValueOperations<String, Object> valueOperations;
    private final RedisCommonUtils redisCommonUtils;

    /**
     * 获取缓存中的值。
     *
     * @param key 缓存键，不能为空
     * @return 缓存中的值
     * @see ValueOperations#get(Object)
     */
    public Object get(@NonNull String key) {
        return valueOperations.get(key);
    }

    /**
     * 将值放入缓存中。
     *
     * @param key   缓存键，不能为空
     * @param value 缓存中的值，不能为空
     * @return true表示成功，false表示失败
     * @see ValueOperations#set(Object, Object)
     */
    public boolean set(@NonNull String key, @NonNull Object value) {
        try {
            valueOperations.set(key, value);
            return true;
        } catch (Exception e) {
            log.error("将值放入缓存中时发生异常 error {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 将值放入缓存中，并设置过期时间。
     *
     * @param key   缓存键，不能为空
     * @param value 缓存中的值，不能为空
     * @param time  过期时间（秒），time要大于0，如果time小于等于0，将设置为无限期
     * @return true表示成功，false表示失败
     * @see ValueOperations#set(Object, Object, long, TimeUnit)
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
            log.error("将值放入缓存并设置过期时间时发生异常 error {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 递增缓存中的值。
     *
     * @param key   缓存键，不能为空
     * @param delta 要增加的值，必须大于0
     * @return 增加后的值，返回null则表示值不是数字类型
     * @throws RuntimeException 如果delta小于等于0或key不存在
     * @see ValueOperations#increment(Object, long)
     */
    public Long increment(@NonNull String key, long delta) {
        if (delta <= 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        if (!redisCommonUtils.exists(key)) {
            throw new RuntimeException("key不存在");
        }
        try {
            return valueOperations.increment(key, delta);
        } catch (Exception e) {
            log.error("递增时发生异常 error {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 递减缓存中的值。
     *
     * @param key   缓存键，不能为空
     * @param delta 要减少的值，必须大于0
     * @return 减少后的值，返回null则表示值不是数字类型
     * @throws RuntimeException 如果delta小于等于0或key不存在
     * @see ValueOperations#decrement(Object, long)
     */
    public Long decrement(@NonNull String key, long delta) {
        if (delta <= 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        if (!redisCommonUtils.exists(key)) {
            throw new RuntimeException("key不存在");
        }
        try {
            return valueOperations.decrement(key, delta);
        } catch (Exception e) {
            log.error("递减时发生异常 error {}", e.getMessage(), e);
        }
        return null;
    }
}
