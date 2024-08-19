package com.biz.cache.caffeine.manager;

import com.biz.cache.caffeine.cache.BizCaffeineCache;
import com.biz.cache.caffeine.convert.CaffeineBuilder;
import com.biz.cache.caffeine.loader.BizCaffeineCacheLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Caffeine缓存的管理者，负责创建和管理CaffeineCache实例。
 * 该管理者只在属性“biz.cache.caffeine-cache”为true时被条件化地启用。
 *
 * @author francis
 * @since 1.0.1
 **/
@ConditionalOnProperty(value = "biz.cache.caffeine-cache", havingValue = "true")
public class BizCaffeineCacheLocalManager implements BizCaffeineCacheManager {

    /**
     * 用于存储CaffeineCache实例的并发映射。
     * 使用ConcurrentHashMap以确保线程安全和高并发性能。
     */
    private static final ConcurrentMap<String, CaffeineCache> CAFFEINE_CACHE_CONCURRENT_MAP = new ConcurrentHashMap<>(32);

    /**
     * 返回所有已创建的CaffeineCache实例。
     *
     * @return CaffeineCache实例的集合。
     */
    @Override
    public Collection<CaffeineCache> getCaffeineCaches() {
        return CAFFEINE_CACHE_CONCURRENT_MAP.values();
    }

    /**
     * 根据BizCaffeineCacheLoader列表创建并返回CaffeineCache实例的集合。
     * 此方法使用@Bean注解，表明它是一个Spring Bean，由Spring容器管理。
     *
     * @param bizCaffeineCacheManagers BizCaffeineCacheLoader的列表，用于配置和创建CaffeineCache。
     * @return CaffeineCache实例的集合。
     */
    @Bean
    private Collection<CaffeineCache> bizCaffeineCacheList(List<BizCaffeineCacheLoader> bizCaffeineCacheManagers) {
        for (BizCaffeineCacheLoader bizCaffeineCacheManager : bizCaffeineCacheManagers) {
            this.convertAndCheckCacheName(bizCaffeineCacheManager.getCaches());
        }
        return CAFFEINE_CACHE_CONCURRENT_MAP.values();
    }


    /**
     * 将BizCaffeineCache实例转换为CaffeineCache，并检查是否存在重复的缓存名称。
     * 此方法负责实际的缓存创建和映射到CAFFEINE_CACHE_CONCURRENT_MAP中。
     *
     * @param caffeineCacheList BizCaffeineCache实例的列表。
     */
    private void convertAndCheckCacheName(List<BizCaffeineCache> caffeineCacheList) {
        for (BizCaffeineCache bizCaffeineCache : caffeineCacheList) {
            this.checkCacheName(bizCaffeineCache.getCacheName());

            CaffeineCache caffeineCache = CaffeineBuilder.builder().setBizCaffeineCache(bizCaffeineCache);
            CAFFEINE_CACHE_CONCURRENT_MAP.putIfAbsent(bizCaffeineCache.getCacheName(), caffeineCache);
        }
    }


    /**
     * 检查指定的缓存名称是否已存在于CAFFEINE_CACHE_CONCURRENT_MAP中。
     * 如果存在，则抛出RuntimeException，指出缓存名称重复。
     *
     * @param cacheName 要检查的缓存名称。
     * @throws RuntimeException 如果缓存名称已存在。
     */
    private void checkCacheName(String cacheName) throws RuntimeException {
        if (CAFFEINE_CACHE_CONCURRENT_MAP.containsKey(cacheName)) {
            throw new RuntimeException("duplicate caffeine cache key");
        }
    }


}
