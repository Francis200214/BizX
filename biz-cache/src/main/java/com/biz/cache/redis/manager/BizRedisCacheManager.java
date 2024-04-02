package com.biz.cache.redis.manager;

import com.biz.cache.redis.cache.BizRedisCacheEntity;

import java.util.Collection;

/**
 * Biz Redis 缓存管理
 *
 * @author francis
 * @create 2024-04-02 11:00
 **/
public interface BizRedisCacheManager {

    /**
     * 获取所有的BizRedisCacheEntity
     *
     * @return
     */
    Collection<BizRedisCacheEntity> getAll();

}
