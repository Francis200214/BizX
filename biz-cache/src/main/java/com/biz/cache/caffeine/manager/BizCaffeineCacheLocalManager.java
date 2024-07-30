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
 * CaffeineCache 抽象管理器
 * 获取 CaffeineCache 并保存本地
 *
 * @author francis
 * @create: 2023-08-21 11:28
 **/
@ConditionalOnProperty(value = "biz.cache.caffeine-cache", havingValue = "true")
public class BizCaffeineCacheLocalManager implements BizCaffeineCacheManager {

    /**
     * CaffeineCache 存储Map
     */
    private static final ConcurrentMap<String, CaffeineCache> CAFFEINE_CACHE_CONCURRENT_MAP = new ConcurrentHashMap<>(32);


    @Override
    public Collection<CaffeineCache> getCaffeineCaches() {
        return CAFFEINE_CACHE_CONCURRENT_MAP.values();
    }


    @Bean
    private Collection<CaffeineCache> getBizCaffeineCacheList(List<BizCaffeineCacheLoader> bizCaffeineCacheManagers) {
        for (BizCaffeineCacheLoader bizCaffeineCacheManager : bizCaffeineCacheManagers) {
            this.convertAndCheckCacheName(bizCaffeineCacheManager.getCaches());
        }
        return CAFFEINE_CACHE_CONCURRENT_MAP.values();
    }


    /**
     * BizCaffeineCache 转换成 CaffeineCache
     * 并检查并存储到 CAFFEINE_CACHE_CONCURRENT_MAP 中
     *
     * @param caffeineCacheList
     * @return
     */
    private void convertAndCheckCacheName(List<BizCaffeineCache> caffeineCacheList) {
        for (BizCaffeineCache bizCaffeineCache : caffeineCacheList) {
            this.checkCacheName(bizCaffeineCache.getCacheName());

            CaffeineCache caffeineCache = CaffeineBuilder.builder().setBizCaffeineCache(bizCaffeineCache);
            CAFFEINE_CACHE_CONCURRENT_MAP.put(bizCaffeineCache.getCacheName(), caffeineCache);
        }
    }


    /**
     * 检查缓存Map中是否已经有了该缓存名称, 有的话抛出 RuntimeException("duplicate caffeine cache key") 错误
     *
     * @param cacheName 缓存名称
     * @throws RuntimeException 缓存Map中已经有了该缓存名称
     */
    private void checkCacheName(String cacheName) throws RuntimeException {
        if (CAFFEINE_CACHE_CONCURRENT_MAP.containsKey(cacheName)) {
            throw new RuntimeException("duplicate caffeine cache key");
        }
    }


}
