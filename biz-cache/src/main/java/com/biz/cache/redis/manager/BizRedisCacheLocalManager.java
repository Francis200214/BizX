package com.biz.cache.redis.manager;

import com.biz.cache.redis.cache.BizRedisCacheEntity;
import com.biz.cache.redis.loader.BizRedisCacheLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Redis Cache Local 管理器
 *
 * @author francis
 * @create 2024-04-02 11:05
 **/
@ConditionalOnProperty(value = "biz.cache.redis-cache", havingValue = "true")
public class BizRedisCacheLocalManager implements BizRedisCacheManager {

    /**
     * Redis Cache 存储Map
     */
    private static final ConcurrentMap<String, BizRedisCacheEntity> CAFFEINE_CACHE_CONCURRENT_MAP = new ConcurrentHashMap<>(32);


    @Override
    public Collection<BizRedisCacheEntity> getAll() {
        return CAFFEINE_CACHE_CONCURRENT_MAP.values();
    }


    @Bean
    private Collection<BizRedisCacheEntity> getBizRedisCacheList(List<BizRedisCacheLoader> redisCacheEntityList) {
        for (BizRedisCacheLoader bizRedisCacheLoader : redisCacheEntityList) {
            this.convertAndCheckCacheNameAndSave(bizRedisCacheLoader.getCaches());
        }
        return CAFFEINE_CACHE_CONCURRENT_MAP.values();
    }

    /**
     * 遍历 List<BizRedisCacheEntity> 并保存在 CAFFEINE_CACHE_CONCURRENT_MAP 中
     *
     * @param redisCacheList
     */
    private void convertAndCheckCacheNameAndSave(List<BizRedisCacheEntity> redisCacheList) {
        for (BizRedisCacheEntity bizRedisCacheEntity : redisCacheList) {
            CAFFEINE_CACHE_CONCURRENT_MAP.put(bizRedisCacheEntity.getCacheName(), bizRedisCacheEntity);
        }
    }

}
