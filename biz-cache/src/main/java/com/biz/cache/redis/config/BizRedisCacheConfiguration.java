package com.biz.cache.redis.config;

import com.biz.cache.redis.cache.BizRedisCacheEntity;
import com.biz.cache.redis.manager.BizRedisCacheManager;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;
import java.util.Collection;

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
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(BizRedisCacheManager bizRedisCacheManager) {
        return (builder) -> {
            Collection<BizRedisCacheEntity> cacheEntities = bizRedisCacheManager.getAll();
            for (BizRedisCacheEntity cacheEntity : cacheEntities) {
                builder.withCacheConfiguration(cacheEntity.getCacheName(),
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(cacheEntity.getTtl())));
            }
        };
    }

}
