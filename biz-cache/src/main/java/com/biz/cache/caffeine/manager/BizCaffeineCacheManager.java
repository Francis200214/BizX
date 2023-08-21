package com.biz.cache.caffeine.manager;

import org.springframework.cache.caffeine.CaffeineCache;

import java.util.List;

/**
 * CaffeineCache 管理器
 * 用于管理 CaffeineCache
 *
 * @author francis
 * @create: 2023-08-21 11:27
 **/
public interface BizCaffeineCacheManager {

    /**
     * 获取所有的 CaffeineCache
     *
     * @return
     */
    List<CaffeineCache> getCaffeineCaches();

}
