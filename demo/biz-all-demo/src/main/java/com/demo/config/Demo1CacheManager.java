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
public class Demo1CacheManager implements BizCaffeineCacheLoader {


    @Override
    public List<BizCaffeineCache> getCaches() {
        return new ArrayList<>();
    }
}
