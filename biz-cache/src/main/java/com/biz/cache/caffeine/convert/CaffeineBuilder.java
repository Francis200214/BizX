package com.biz.cache.caffeine.convert;

import com.biz.cache.caffeine.cache.BizCaffeineCache;
import com.biz.common.utils.Common;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import org.springframework.cache.caffeine.CaffeineCache;

/**
 * 使用Caffeine构建Cache对象
 *
 * @author francis
 * @create: 2023-08-21 14:26
 **/
public class CaffeineBuilder {


    /**
     * CaffeineSpec parse 字符串
     */
    private static StringBuilder caffeineSpecStringBuilder = new StringBuilder();

    /**
     * = 符号
     */
    private static final String COMMON_EQUAL_TO = "=";

    /**
     * , 符合
     */
    private static final String COMMON_COMMA = ",";

    /**
     * 秒
     */
    private static final String SECOND = "s";

    /**
     * 缓存名称
     */
    private String cacheName;

    /**
     * 初始容量 字符串
     */
    private static final String INITIAL_CAPACITY_STR = "initialCapacity";

    /**
     * 最大容量 字符串
     */
    private static final String MAXIMUM_SIZE_STR = "maximumSize";

    /**
     * 最大权重 字符串
     */
    private static final String MAXIMUM_WEIGHT_STR = "maximumWeight";

    /**
     * 写入后过期时间 字符串
     */
    private static final String EXPIRE_AFTER_WRITE_STR = "expireAfterWrite";

    /**
     * 访问后过期时间 字符串
     */
    private static final String EXPIRE_AFTER_ACCESS_STR = "expireAfterAccess";

    /**
     * 写入后刷新时间 字符串
     */
    private static final String REFRESH_AFTER_WRITE_STR = "refreshAfterWrite";


    public static CaffeineBuilder builder() {
        return new CaffeineBuilder();
    }

    /**
     * 设置 缓存Key
     *
     * @param cacheName
     * @return
     */
    public CaffeineBuilder setCacheName(String cacheName) {
        this.cacheName = cacheName;
        return this;
    }

    /**
     * 设置 初始容量
     *
     * @param initialCapacity
     * @return
     */
    public CaffeineBuilder setInitialCapacity(Integer initialCapacity) {
        if (initialCapacity != null) {
            caffeineSpecStringBuilder.append(INITIAL_CAPACITY_STR)
                    .append(COMMON_EQUAL_TO)
                    .append(initialCapacity)
                    .append(COMMON_COMMA);
        }
        return this;
    }

    /**
     * 设置 最大容量
     *
     * @param maximumSize
     * @return
     */
    public CaffeineBuilder setMaximumSize(Long maximumSize) {
        if (maximumSize != null) {
            caffeineSpecStringBuilder.append(MAXIMUM_SIZE_STR)
                    .append(COMMON_EQUAL_TO)
                    .append(maximumSize)
                    .append(COMMON_COMMA);
        }
        return this;
    }


    /**
     * 设置 最大权重
     *
     * @param maximumWeight
     * @return
     */
    public CaffeineBuilder setMaximumWeight(Long maximumWeight) {
        if (maximumWeight != null) {
            caffeineSpecStringBuilder.append(MAXIMUM_WEIGHT_STR)
                    .append(COMMON_EQUAL_TO)
                    .append(maximumWeight)
                    .append(COMMON_COMMA);
        }
        return this;
    }

    /**
     * 设置 访问后过期时间
     *
     * @param expireAfterAccess
     * @return
     */
    public CaffeineBuilder setExpireAfterAccess(Long expireAfterAccess) {
        if (expireAfterAccess != null) {
            caffeineSpecStringBuilder.append(EXPIRE_AFTER_ACCESS_STR)
                    .append(COMMON_EQUAL_TO)
                    .append(expireAfterAccess)
                    .append(SECOND)
                    .append(COMMON_COMMA);
        }
        return this;
    }

    /**
     * 设置 写入后过期时间
     *
     * @param expireAfterWrite
     * @return
     */
    public CaffeineBuilder setExpireAfterWrite(Long expireAfterWrite) {
        if (expireAfterWrite != null) {
            caffeineSpecStringBuilder.append(EXPIRE_AFTER_WRITE_STR)
                    .append(COMMON_EQUAL_TO)
                    .append(expireAfterWrite)
                    .append(SECOND)
                    .append(COMMON_COMMA);
        }
        return this;
    }


    /**
     * 写入后刷新时间
     *
     * @param refreshAfterWrite
     * @return
     */
    public CaffeineBuilder setRefreshAfterWrite(Long refreshAfterWrite) {
        if (refreshAfterWrite != null) {
            caffeineSpecStringBuilder.append(REFRESH_AFTER_WRITE_STR)
                    .append(COMMON_EQUAL_TO)
                    .append(refreshAfterWrite)
                    .append(SECOND)
                    .append(COMMON_COMMA);
        }
        return this;
    }

    /**
     * 通过 自定义方法参数构建
     *
     * @return
     */
    public CaffeineCache build() {
        if (Common.isBlank(cacheName)) {
            throw new RuntimeException("caffeine cache name is not null");
        }
        return new CaffeineCache(cacheName, Caffeine.from(CaffeineSpec.parse(caffeineSpecStringBuilder.toString())).build());
    }

    /**
     * 设置 BizCaffeineCache 实体
     * 设置 BizCaffeineCache 实体后就不用在设置其他的属性
     *
     * @param bizCaffeineCache
     * @return
     */
    public CaffeineCache setBizCaffeineCache(BizCaffeineCache bizCaffeineCache) {
        if (Common.isBlank(bizCaffeineCache.getCacheName())) {
            throw new RuntimeException("caffeine cache name is not null");
        }

        return this.setCacheName(bizCaffeineCache.getCacheName())
                .setInitialCapacity(bizCaffeineCache.getInitialCapacity())
                .setMaximumSize(bizCaffeineCache.getMaximumSize())
                .setMaximumWeight(bizCaffeineCache.getMaximumWeight())
                .setExpireAfterAccess(bizCaffeineCache.getExpireAfterAccess())
                .setExpireAfterWrite(bizCaffeineCache.getExpireAfterWrite())
                .setRefreshAfterWrite(bizCaffeineCache.getRefreshAfterWrite())
                .build();
    }


}
