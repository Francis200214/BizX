package com.biz.cache.redis.cache;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * BizCaffeineCache 实体
 *
 * @author francis
 * @since 1.0.1
 **/
@Setter
@Getter
@ToString
@Builder
public class BizRedisCacheEntity implements Serializable {

    /**
     * 缓存名称
     */
    private String cacheName;

    /**
     * 缓存时间
     */
    private Long ttl;

}
