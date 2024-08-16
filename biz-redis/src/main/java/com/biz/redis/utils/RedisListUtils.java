package com.biz.redis.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;

import java.util.List;
import java.util.Optional;

/**
 * Redis List Utils
 * <p>提供对Redis列表（List）数据结构的常用操作方法，包括获取列表内容、获取列表长度、通过索引获取值、添加和更新列表值、以及移除列表中的值等。</p>
 *
 * <p>该类依赖于{@link ListOperations}和{@link RedisCommonUtils}。</p>
 *
 * @see ListOperations
 * @see RedisCommonUtils
 * @see <a href="https://redis.io/commands#list">Redis List Commands</a>
 * @author francis
 * @version 1.4.11
 * @since 1.0.1
 */
@Slf4j
@RequiredArgsConstructor
public class RedisListUtils {

    private final ListOperations<String, Object> listOperations;
    private final RedisCommonUtils redisCommonUtils;

    /**
     * 获取列表缓存的内容。
     *
     * @param key   缓存键
     * @param start 开始位置
     * @param end   结束位置（0 到 -1代表所有值）
     * @return 列表中的值
     * @see ListOperations#range(Object, long, long)
     */
    public List<Object> getList(@NonNull String key, long start, long end) {
        try {
            return listOperations.range(key, start, end);
        } catch (Exception e) {
            log.error("获取列表缓存的内容时发生异常 error {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取列表缓存的长度。
     *
     * @param key 缓存键
     * @return 列表长度
     * @see ListOperations#size(Object)
     */
    public long getListSize(@NonNull String key) {
        try {
            return Optional.ofNullable(listOperations.size(key)).orElse(0L);
        } catch (Exception e) {
            log.error("获取列表缓存的长度时发生异常 error {}", e.getMessage(), e);
        }
        return 0;
    }

    /**
     * 通过索引获取列表中的值。
     *
     * @param key   缓存键
     * @param index 索引（index>=0时，0 表头，1 第二个元素，依次类推；index<0时，-1 表尾，-2 倒数第二个元素，依次类推）
     * @return 列表中的值
     * @see ListOperations#index(Object, long)
     */
    public Object getIndex(@NonNull String key, long index) {
        try {
            return listOperations.index(key, index);
        } catch (Exception e) {
            log.error("通过索引获取列表中的值时发生异常 error {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将值添加到列表缓存中。
     *
     * @param key   缓存键
     * @param value 值
     * @return 操作是否成功
     * @see ListOperations#rightPush(Object, Object)
     */
    public boolean addToList(@NonNull String key, @NonNull Object value) {
        try {
            listOperations.rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("将值添加到列表缓存时发生异常 error {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 将值添加到列表缓存中，并设置过期时间。
     *
     * @param key   缓存键
     * @param value 值
     * @param time  过期时间（秒）
     * @return 操作是否成功
     * @see ListOperations#rightPush(Object, Object)
     */
    public boolean addToList(@NonNull String key, @NonNull Object value, long time) {
        try {
            listOperations.rightPush(key, value);
            redisCommonUtils.setExpire(key, time);
            return true;
        } catch (Exception e) {
            log.error("将值添加到列表缓存并设置过期时间时发生异常 error {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 将多个值添加到列表缓存中。
     *
     * @param key   缓存键
     * @param value 值列表
     * @return 操作是否成功
     * @see ListOperations#rightPushAll(Object, Object[])
     */
    public boolean addToList(@NonNull String key, @NonNull List<Object> value) {
        try {
            listOperations.rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("将多个值添加到列表缓存时发生异常 error {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 将多个值添加到列表缓存中，并设置过期时间。
     *
     * @param key   缓存键
     * @param value 值列表
     * @param time  过期时间（秒）
     * @return 操作是否成功
     * @see ListOperations#rightPushAll(Object, Object[])
     */
    public boolean addToList(@NonNull String key, @NonNull List<Object> value, long time) {
        try {
            listOperations.rightPushAll(key, value);
            redisCommonUtils.setExpire(key, time);
            return true;
        } catch (Exception e) {
            log.error("将多个值添加到列表缓存并设置过期时间时发生异常 error {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 根据索引修改列表中的某条数据。
     *
     * @param key   缓存键
     * @param index 索引
     * @param value 值
     * @return 操作是否成功
     * @see ListOperations#set(Object, long, Object)
     */
    public boolean updateListAtIndex(@NonNull String key, long index, @NonNull Object value) {
        try {
            listOperations.set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("根据索引修改列表中的某条数据时发生异常 error {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 移除列表中N个值为指定值的元素。
     *
     * @param key   缓存键
     * @param count 要移除的元素数量
     * @param value 要移除的值
     * @return 移除的个数
     * @see ListOperations#remove(Object, long, Object)
     */
    public void removeFromList(@NonNull String key, long count, @NonNull Object value) {
        try {
            listOperations.remove(key, count, value);
        } catch (Exception e) {
            log.error("移除列表中N个值为指定值的元素时发生异常 error {}", e.getMessage(), e);
        }
    }
}
