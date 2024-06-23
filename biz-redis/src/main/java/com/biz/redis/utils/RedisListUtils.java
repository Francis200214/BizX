package com.biz.redis.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;

import java.util.List;
import java.util.Optional;

/**
 * Redis List Utils
 *
 * @author francis
 * @create 2024-04-02 17:17
 **/
@Slf4j
@RequiredArgsConstructor
public class RedisListUtils {

    private final ListOperations<String, Object> listOperations;

    private final RedisCommonUtils redisCommonUtils;

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(@NonNull String key, long start, long end) {
        try {
            return listOperations.range(key, start, end);

        } catch (Exception e) {
            log.error("获取list缓存的内容 出现异常 error {}", e.getMessage(), e);
        }

        return null;
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(@NonNull String key) {
        try {
            return Optional.ofNullable(listOperations.size(key))
                    .orElse(0L);

        } catch (Exception e) {
            log.error("获取list缓存的长度 出现异常 error {}", e.getMessage(), e);
        }

        return 0;
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(@NonNull String key, long index) {
        try {
            return listOperations.index(key, index);

        } catch (Exception e) {
            log.error("通过索引 获取list中的值 出现异常 error {}", e.getMessage(), e);
        }

        return null;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(@NonNull String key, @NonNull Object value) {
        try {
            listOperations.rightPush(key, value);
            return true;

        } catch (Exception e) {
            log.error("将list放入缓存 出现异常 error {}", e.getMessage(), e);
        }

        return false;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(@NonNull String key, @NonNull Object value, long time) {
        try {
            listOperations.rightPush(key, value);
            redisCommonUtils.expire(key, time);
            return true;

        } catch (Exception e) {
            log.error("将list放入缓存 出现异常 error {}", e.getMessage(), e);
        }

        return false;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(@NonNull String key, @NonNull List<Object> value) {
        try {
            listOperations.rightPushAll(key, value);
            return true;

        } catch (Exception e) {
            log.error("将list放入缓存 出现异常 error {}", e.getMessage(), e);
        }

        return false;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(@NonNull String key, @NonNull List<Object> value, long time) {
        try {
            listOperations.rightPushAll(key, value);
            redisCommonUtils.expire(key, time);
            return true;

        } catch (Exception e) {
            log.error("将list放入缓存 出现异常 error {}", e.getMessage(), e);
        }

        return false;
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(@NonNull String key, long index, @NonNull Object value) {
        try {
            listOperations.set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("根据索引修改list中的某条数据 出现异常 error {}", e.getMessage(), e);
        }

        return false;
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public void lRemove(@NonNull String key, long count, @NonNull Object value) {
        try {
            listOperations.remove(key, count, value);

        } catch (Exception e) {
            log.error("移除N个值为value 出现异常 error {}", e.getMessage(), e);
        }
    }

}
