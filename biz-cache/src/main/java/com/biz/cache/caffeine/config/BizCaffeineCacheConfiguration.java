package com.biz.cache.caffeine.config;

import com.biz.cache.caffeine.manager.BizCaffeineCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

/**
 * CaffeineCache 配置类
 *
 * @author francis
 * @create: 2023-08-19 15:08
 **/
@ConditionalOnProperty(value = "biz.cache.caffeine-cache", havingValue = "true")
public class BizCaffeineCacheConfiguration {

    private static final SimpleCacheManager SIMPLE_CACHE_MANAGER = new SimpleCacheManager();

    @Bean
    @DependsOn("getBizCaffeineCacheList")
    public CacheManager caffeineCacheManager(BizCaffeineCacheManager bizCaffeineCacheManager) {
        SIMPLE_CACHE_MANAGER.setCaches(bizCaffeineCacheManager.getCaffeineCaches());
        return SIMPLE_CACHE_MANAGER;
    }

}


