package com.biz.cache.caffeine.manager;

import com.biz.cache.caffeine.cache.BizCaffeineCache;
import com.biz.cache.caffeine.convert.CaffeineBuilder;
import com.biz.cache.caffeine.loader.BizCaffeineCacheLoader;
import com.biz.common.utils.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

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
@Component
public class BizCaffeineCacheManagerAbstract implements BizCaffeineCacheManager {


    @Autowired
    private BizCaffeineCacheLoader bizCaffeineCacheLoader;


    private static final ConcurrentMap<String, CaffeineCache> CAFFEINE_CACHE_CONCURRENT_MAP = new ConcurrentHashMap<>(32);


    @Override
    public List<CaffeineCache> getCaffeineCaches() {
        return convertAndCheckCacheName(getBizCaffeineCaches());
    }


    /**
     * 获取所有的 CaffeineCache 缓存实体
     *
     * @return
     */
    private List<BizCaffeineCache> getBizCaffeineCaches() {
        return bizCaffeineCacheLoader.getCaches();
    }


    private List<CaffeineCache> convertAndCheckCacheName(List<BizCaffeineCache> caffeineCacheList) {
        for (BizCaffeineCache bizCaffeineCache : caffeineCacheList) {
            if (CAFFEINE_CACHE_CONCURRENT_MAP.containsKey(bizCaffeineCache.getCacheName())) {
                throw new RuntimeException("duplicate caffeine cache key");
            }

            CaffeineCache caffeineCache = CaffeineBuilder.builder().setBizCaffeineCache(bizCaffeineCache);
            CAFFEINE_CACHE_CONCURRENT_MAP.put(bizCaffeineCache.getCacheName(), caffeineCache);
        }

        return Common.to(CAFFEINE_CACHE_CONCURRENT_MAP.values());
    }


}
