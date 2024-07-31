package com.biz.cache.redis.config;

import com.biz.cache.redis.cache.BizRedisCacheEntity;
import com.biz.cache.redis.manager.BizRedisCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

/**
 * Redis缓存配置类，条件注解确保只有在配置属性biz.cache.redis-cache为true时才加载该配置。
 *
 * @author francis
 * @create 2024-04-01 17:23
 **/
@ConditionalOnProperty(value = "biz.cache.redis-cache", havingValue = "true")
public class BizRedisCacheConfiguration {

    /**
     * Redis连接工厂，用于创建Redis缓存管理器。
     */
    private final RedisConnectionFactory redisConnectionFactory;

    /**
     * 构造函数，注入Redis连接工厂。
     *
     * @param redisConnectionFactory Redis连接工厂
     */
    public BizRedisCacheConfiguration(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    /**
     * 配置RedisCacheManager bean，依赖于bizRedisCacheList。
     *
     * @param bizRedisCacheManager Redis缓存管理器，用于获取所有缓存配置。
     * @return RedisCacheManager实例，配置了多个缓存配置。
     */
    @Bean
    @DependsOn("bizRedisCacheList")
    public RedisCacheManager redisCacheManager(BizRedisCacheManager bizRedisCacheManager) {
        Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();
        Optional.ofNullable(bizRedisCacheManager.getAll()).ifPresent(caches -> {
            caches.forEach(bizRedisCacheEntity -> {
                validateAndAddCacheConfiguration(cacheConfigurationMap, bizRedisCacheEntity);
            });
        });

        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(defaultCacheConfig())
                .withInitialCacheConfigurations(cacheConfigurationMap)
                .transactionAware()
                .build();
    }

    /**
     * 校验并添加缓存配置，确保缓存名称不重复。
     *
     * @param cacheConfigurationMap 存储所有缓存配置的映射。
     * @param bizRedisCacheEntity   要添加的缓存实体。
     */
    private void validateAndAddCacheConfiguration(Map<String, RedisCacheConfiguration> cacheConfigurationMap, BizRedisCacheEntity bizRedisCacheEntity) {
        if (cacheConfigurationMap.containsKey(bizRedisCacheEntity.getCacheName())) {
            throw new IllegalStateException("Redis缓存配置初始化时发现缓存名称重复: " + bizRedisCacheEntity.getCacheName());
        }
        cacheConfigurationMap.put(
                bizRedisCacheEntity.getCacheName(),
                buildCacheConfiguration(bizRedisCacheEntity));
    }

    /**
     * 根据BizRedisCacheEntity构建RedisCacheConfiguration。
     *
     * @param bizRedisCacheEntity 缓存实体。
     * @return RedisCacheConfiguration实例。
     */
    private RedisCacheConfiguration buildCacheConfiguration(BizRedisCacheEntity bizRedisCacheEntity) {
        return defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(bizRedisCacheEntity.getTtl()))
                .disableCachingNullValues();
    }

}
