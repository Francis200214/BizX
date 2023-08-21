package com.biz.cache.caffeine.config;

import com.biz.cache.caffeine.manager.BizCaffeineCacheManager;
import com.biz.common.serviceloader.ServiceLoaderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * CaffeineCache 配置类
 *
 * @author francis
 * @create: 2023-08-19 15:08
 **/
@Configuration
public class BizCaffeineCacheConfiguration implements Ordered {

    private static final SimpleCacheManager SIMPLE_CACHE_MANAGER = new SimpleCacheManager();

    @Autowired
    private BizCaffeineCacheManager bizCaffeineCacheManager;

    @Bean
    @ConditionalOnProperty(value = "biz.cache.caffeineCache", havingValue = "true")
    public CacheManager caffeineCacheManager() {
        SIMPLE_CACHE_MANAGER.setCaches(bizCaffeineCacheManager.getCaffeineCaches());
        return SIMPLE_CACHE_MANAGER;
    }

    @Override
    public int getOrder() {
        return 50;
    }
}


