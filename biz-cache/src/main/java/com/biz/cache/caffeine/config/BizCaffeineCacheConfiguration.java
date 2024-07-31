package com.biz.cache.caffeine.config;

import com.biz.cache.caffeine.manager.BizCaffeineCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

/**
 * 据属性条件启用的Caffeine缓存配置类。
 * 当属性"biz.cache.caffeine-cache"为true时，此配置类生效。
 *
 * @author francis
 * @create: 2023-08-19 15:08
 **/
@ConditionalOnProperty(value = "biz.cache.caffeine-cache", havingValue = "true")
public class BizCaffeineCacheConfiguration {

    /**
     * 配置Caffeine缓存管理器。
     * 此方法依赖于"getBizCaffeineCacheList"方法的执行，确保BizCaffeineCacheManager已经被初始化。
     *
     * @param bizCaffeineCacheManager Caffeine缓存管理器，提供Caffeine缓存实例的集合。
     * @return 返回配置好的SimpleCacheManager实例，用于Spring应用上下文中。
     */
    @Bean
    @DependsOn("getBizCaffeineCacheList")
    public CacheManager caffeineCacheManager(BizCaffeineCacheManager bizCaffeineCacheManager) {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(bizCaffeineCacheManager.getCaffeineCaches());
        return simpleCacheManager;
    }

}


