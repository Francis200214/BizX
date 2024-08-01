package com.biz.cache.caffeine.cache;

import lombok.*;

import java.io.Serializable;

/**
 * BizCaffeineCache 实体
 *
 * @author francis
 * @since 2023-08-20 11:14
 **/
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizCaffeineCache implements Serializable {

    /**
     * 缓存名称
     */
    private String cacheName;

    /**
     * 初始容量, 表示缓存的初始大小, 默认值为16.
     */
    private Integer initialCapacity;

    /**
     * 最大条目数, 表示缓存可以容纳的最大条目数, 默认值为10000.
     */
    private Long maximumSize;

    /**
     * 最大权重, 表示缓存可以容纳的最大权重总和, 在权重模式下使用, 可以根据条目的权重进行限制.
     * 单位是秒
     */
    private Long maximumWeight;

    /**
     * 访问后过期时间, 表示条目在被访问一段时间后过期, 默认不过期.
     * 单位是秒
     */
    private Long expireAfterAccess;

    /**
     * 写入后过期时间, 表示条目在被写入一段时间后过期, 默认不过期.
     * 单位是秒
     */
    private Long expireAfterWrite;

    /**
     * 写入后自动刷新时间, 表示条目在被写入一段时间后自动刷新, 默认不刷新.
     * 单位是秒
     */
    private Long refreshAfterWrite;

}
