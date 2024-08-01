package com.biz.cache.caffeine.loader;

import com.biz.cache.caffeine.cache.BizCaffeineCache;

import java.util.List;

/**
 * BizCaffeineCache 缓存加载器
 *
 * @author francis
 * @since 2023-08-21 09:46
 **/
public interface BizCaffeineCacheLoader {

    /**
     * 获取加载到的 BizCaffeineCache 实体
     *
     * @return
     */
    List<BizCaffeineCache> getCaches();

}
