package com.demo.config;

import com.biz.cache.redis.cache.BizRedisCacheEntity;
import com.biz.cache.redis.loader.BizRedisCacheLoader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author francis
 * @since 2023-08-21 17:03
 **/
@Component
public class DemoRedisCacheManager1 implements BizRedisCacheLoader {


    @Override
    public List<BizRedisCacheEntity> getCaches() {
        List<BizRedisCacheEntity> result = new ArrayList<>();
        result.add(BizRedisCacheEntity.builder()
                .cacheName("userCache")
                .ttl(30L)
                .build());
        return result;
    }


}
