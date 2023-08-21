package com.demo.cocnfig;

import com.biz.cache.caffeine.cache.BizCaffeineCache;
import com.biz.cache.caffeine.loader.BizCaffeineCacheExtendClassLoader;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author francis
 * @create: 2023-08-21 17:03
 **/
@Component
public class CacheManager extends BizCaffeineCacheExtendClassLoader {

    @Override
    protected List<BizCaffeineCache> getCacheList() {
        return null;
    }

}
