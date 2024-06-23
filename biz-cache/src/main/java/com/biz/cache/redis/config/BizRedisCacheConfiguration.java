package com.biz.cache.redis.config;

import com.biz.cache.redis.cache.BizRedisCacheEntity;
import com.biz.cache.redis.manager.BizRedisCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

/**
 * RedisCache 配置
 *
 * @author francis
 * @create 2024-04-01 17:23
 **/
@ConditionalOnProperty(value = "biz.cache.redis-cache", havingValue = "true")
public class BizRedisCacheConfiguration {

    @Bean
    @DependsOn("getBizRedisCacheList")
    public RedisCacheManager redisRedisCacheManager(BizRedisCacheManager bizRedisCacheManager) {
        Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();
        for (BizRedisCacheEntity bizRedisCacheEntity : bizRedisCacheManager.getAll()) {
            if (cacheConfigurationMap.containsKey(bizRedisCacheEntity.getCacheName())) {
                throw new RuntimeException("Redis缓存配置初始化时发现缓存名称重复！");
            }
            cacheConfigurationMap.put(
                    bizRedisCacheEntity.getCacheName(),
                    defaultCacheConfig()
                            .entryTtl(Duration.ofSeconds(bizRedisCacheEntity.getTtl()))
                            .disableCachingNullValues());
        }

        return RedisCacheManager.builder()
                .cacheDefaults(defaultCacheConfig())
                .withInitialCacheConfigurations(cacheConfigurationMap)
                .transactionAware()
                .build();
    }

}
