package com.demo.cocnfig;

import com.demo.controller.UserController;

/**
 * 缓存配置
 *
 * @author francis
 * @create: 2023-08-19 15:05
 **/
public enum CacheNameEnum {

    /**
     * 用户
     */
    USER_CACHE(UserController.USER_CACHE, 5, 20L, 60L);

    private final String name;
    private final Integer initialCapacity;
    private final Long maximumSize;
    private final Long expire;

    CacheNameEnum(String name, Integer initialCapacity, Long maximumSize, Long expire) {
        this.name = name;
        this.initialCapacity = initialCapacity;
        this.maximumSize = maximumSize;
        this.expire = expire;
    }

    public Long getMaximumSize() {
        return maximumSize;
    }

    public Integer getInitialCapacity() {
        return initialCapacity;
    }

    public Long getExpire() {
        return expire;
    }


    public String getName() {
        return name;
    }

}
