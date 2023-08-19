package com.demo.cocnfig;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author francis
 * @create: 2023-08-19 15:08
 **/
@Configuration
public class CaffeineCacheConfig {
    private static final SimpleCacheManager SIMPLE_CACHE_MANAGER = new SimpleCacheManager();

    @Bean
    public CacheManager caffeineCacheManager() {

        List<CaffeineCache> caches = new ArrayList<>();


        for (CacheNameEnum value : CacheNameEnum.values()) {

            com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = Caffeine.newBuilder()
                    .initialCapacity(value.getInitialCapacity())
                    .maximumSize(value.getMaximumSize())
                    //写入后失效时间
                    .expireAfterWrite(Duration.ofSeconds(value.getExpire()))
                    .build();

            caches.add(new CaffeineCache(value.getName(), cache));
        }

        SIMPLE_CACHE_MANAGER.setCaches(caches);

        return SIMPLE_CACHE_MANAGER;
    }

}
