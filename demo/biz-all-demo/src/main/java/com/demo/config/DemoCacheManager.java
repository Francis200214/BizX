package com.demo.config;

import com.biz.cache.caffeine.cache.BizCaffeineCache;
import com.biz.cache.caffeine.loader.BizCaffeineCacheLoader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author francis
 * @since 1.0.1
 **/
@Component
public class DemoCacheManager implements BizCaffeineCacheLoader {


    @Override
    public List<BizCaffeineCache> getCaches() {
        List<BizCaffeineCache> result = new ArrayList<>();
        result.add(BizCaffeineCache.builder()
                .cacheName("userCache")
                .expireAfterWrite(30L)
                .build());
        return result;
    }



}
