package com.biz.cache.redis.manager;

import com.biz.cache.redis.cache.BizRedisCacheEntity;

import java.util.Collection;

/**
 * BizRedisCacheManager接口定义了业务Redis缓存的管理操作。
 * 它提供方法来获取所有的BizRedisCacheEntity实例。
 *
 * @author francis
 * @since 2024-04-02 11:00
 **/
public interface BizRedisCacheManager {

    /**
     * 获取当前缓存中所有的BizRedisCacheEntity对象。
     * <p>
     * 该方法用于获取缓存中的所有实体，以便进行批量处理或查询。
     *
     * @return 所有缓存的BizRedisCacheEntity对象的集合。
     */
    Collection<BizRedisCacheEntity> getAll();

}
