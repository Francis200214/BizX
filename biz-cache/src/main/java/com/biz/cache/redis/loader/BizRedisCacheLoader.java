package com.biz.cache.redis.loader;

import com.biz.cache.redis.cache.BizRedisCacheEntity;

import java.util.List;

/**
 * Biz Redis Cache 缓存实体加载器
 *
 * @author francis
 * @since 1.0.1
 **/
public interface BizRedisCacheLoader {

    /**
     * 获取加载到的 BizCaffeineCache 实体
     *
     * @return
     */
    List<BizRedisCacheEntity> getCaches();

}
