package com.biz.cache.caffeine.manager;

import org.springframework.cache.caffeine.CaffeineCache;

import java.util.Collection;

/**
 * CaffeineCache 管理器接口，用于提供对多个 CaffeineCache 实例的集中管理功能。
 *
 * @author francis
 * @since 1.0.1
 **/
public interface BizCaffeineCacheManager {

    /**
     * 获取所有当前管理的 CaffeineCache 实例集合。
     *
     * @return Collection<CaffeineCache> 包含所有 CaffeineCache 实例的集合
     */
    Collection<CaffeineCache> getCaffeineCaches();

}
