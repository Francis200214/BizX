package com.biz.cache.enums;

import lombok.Getter;

/**
 * 缓存实体类型枚举
 *
 * @author francis
 * @since 1.0.1
 **/
@Getter
public enum CacheEntityClassEnum {

    /**
     * Biz 缓存实体
     */
    BIZ_CACHE,

    /**
     * Caffeine 缓存实体
     */
    CAFFEINE_CACHE;

}
