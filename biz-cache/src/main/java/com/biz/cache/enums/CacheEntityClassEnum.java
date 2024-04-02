package com.biz.cache.enums;

import lombok.Getter;

/**
 * 缓存实体类型枚举
 *
 * @author francis
 * @create: 2023-08-21 10:20
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
