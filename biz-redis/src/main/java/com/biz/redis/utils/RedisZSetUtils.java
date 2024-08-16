package com.biz.redis.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * Redis ZSet Utils 提供对Redis有序集合（ZSet）数据结构的常用操作方法。
 * 包括存储有序集合、获取指定范围的值、获取所有值以及获取集合大小等。
 *
 * <p>该类依赖于{@link ZSetOperations}和{@link RedisCommonUtils}。</p>
 *
 * <pre>
 * 示例：
 * RedisZSetUtils redisZSetUtils = new RedisZSetUtils(zSetOperations, redisCommonUtils);
 * redisZSetUtils.zSet("key", value, score);
 * </pre>
 *
 * <p>注意：所有操作均假定键和值不为null，且对于可能出现的异常进行了日志记录。</p>
 *
 * @see ZSetOperations
 * @see RedisCommonUtils
 * @see <a href="https://redis.io/commands#zset">Redis ZSet Commands</a>
 * @author francis
 * @version 1.4.11
 * @since 1.0.1
 */
@Slf4j
@RequiredArgsConstructor
public class RedisZSetUtils {

    private final ZSetOperations<String, Object> zSetOperations;
    private final RedisCommonUtils redisCommonUtils;

    /**
     * 存储有序集合中的一个值。
     *
     * @param key   集合的键，不能为空
     * @param value 集合中的值，不能为空
     * @param score 值的分数，用于排序
     * @see ZSetOperations#add(Object, Object, double)
     */
    public void addZSet(@NonNull String key, @NonNull Object value, double score) {
        zSetOperations.add(key, value, score);
    }

    /**
     * 存储有序集合中的多个值。
     *
     * @param key 集合的键，不能为空
     * @param set 集合中的多个值，不能为空
     * @see ZSetOperations#add(Object, Set)
     */
    public void addZSet(@NonNull String key, @NonNull Set<ZSetOperations.TypedTuple<Object>> set) {
        zSetOperations.add(key, set);
    }

    /**
     * 获取有序集合中指定范围的值。
     *
     * @param key   集合的键，不能为空
     * @param start 开始位置
     * @param end   结束位置
     * @return 有序集合中的值
     * @see ZSetOperations#range(Object, long, long)
     */
    public Set<Object> getZSetRange(@NonNull String key, long start, long end) {
        return zSetOperations.range(key, start, end);
    }

    /**
     * 获取有序集合中的所有值。
     *
     * @param key 集合的键，不能为空
     * @return 有序集合中的所有值
     * @see ZSetOperations#range(Object, long, long)
     */
    public Set<Object> getZSetAll(@NonNull String key) {
        return zSetOperations.range(key, 0, -1);
    }

    /**
     * 获取有序集合的大小。
     *
     * @param key 集合的键，不能为空
     * @return 有序集合的大小
     * @see ZSetOperations#size(Object)
     */
    public Long getZSetSize(@NonNull String key) {
        return zSetOperations.size(key);
    }
}
