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
 * Redis缓存本地管理器，实现BizRedisCacheManager接口。
 * <p>
 * 该类负责管理Redis缓存实体，在Spring Boot自动配置的条件下激活。
 *
 * @author francis
 * @since 2024-04-02 11:05
 **/
@ConditionalOnProperty(value = "biz.cache.redis-cache", havingValue = "true")
public class BizRedisCacheLocalManager implements BizRedisCacheManager {

    /**
     * 使用ConcurrentMap来存储Redis缓存实体，以保证线程安全。
     * 初始化容量为32，以提高并发性能。
     */
    private static final ConcurrentMap<String, BizRedisCacheEntity> CAFFEINE_CACHE_CONCURRENT_MAP = new ConcurrentHashMap<>(32);

    /**
     * 获取所有缓存实体。
     *
     * @return 所有缓存实体的集合。
     */
    @Override
    public Collection<BizRedisCacheEntity> getAll() {
        return CAFFEINE_CACHE_CONCURRENT_MAP.values();
    }

    /**
     * 根据提供的BizRedisCacheLoader列表初始化缓存实体集合。
     * 此方法被Spring Boot自动配置为一个Bean。
     *
     * @param redisCacheEntityList Redis缓存实体的列表。
     * @return 初始化后的缓存实体集合。
     */
    @Bean
    private Collection<BizRedisCacheEntity> bizRedisCacheList(List<BizRedisCacheLoader> redisCacheEntityList) {
        for (BizRedisCacheLoader bizRedisCacheLoader : redisCacheEntityList) {
            this.convertAndCheckCacheNameAndSave(bizRedisCacheLoader.getCaches());
        }
        return CAFFEINE_CACHE_CONCURRENT_MAP.values();
    }

    /**
     * 将给定的Redis缓存实体列表转换并保存到ConcurrentMap中。
     * 此方法用于验证和保存Redis缓存配置。
     *
     * @param redisCacheList 待保存的Redis缓存实体列表。
     */
    private void convertAndCheckCacheNameAndSave(List<BizRedisCacheEntity> redisCacheList) {
        for (BizRedisCacheEntity bizRedisCacheEntity : redisCacheList) {
            CAFFEINE_CACHE_CONCURRENT_MAP.put(bizRedisCacheEntity.getCacheName(), bizRedisCacheEntity);
        }
    }

}
