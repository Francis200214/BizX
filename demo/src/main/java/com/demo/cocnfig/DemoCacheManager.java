package com.demo.cocnfig;

import com.biz.cache.caffeine.cache.BizCaffeineCache;
import com.biz.cache.caffeine.loader.BizCaffeineCacheLoader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author francis
 * @create: 2023-08-21 17:03
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
