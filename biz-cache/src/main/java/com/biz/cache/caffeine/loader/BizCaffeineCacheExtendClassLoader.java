package com.biz.cache.caffeine.loader;

import com.biz.cache.caffeine.cache.BizCaffeineCache;
import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.reflection.ReflectionUtils;
import com.biz.common.utils.Common;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * CaffeineCache 继承类方式缓存加载器
 *
 * @author francis
 * @create: 2023-08-21 09:49
 **/
public abstract class BizCaffeineCacheExtendClassLoader implements BizCaffeineCacheLoader {


    @Override
    public List<BizCaffeineCache> getCaches() {
        List<BizCaffeineCache> cacheList = new ArrayList<>();
        for (Class<?> beanDefinitionClass : BizXBeanUtils.getBeanDefinitionClasses()) {
            if (ReflectionUtils.isImplementsClass(BizCaffeineCacheExtendClassLoader.class, beanDefinitionClass)) {
                Class<? extends BizCaffeineCacheExtendClassLoader> beanClass = Common.to(beanDefinitionClass);
                BizCaffeineCacheExtendClassLoader bean = BizXBeanUtils.getBean(beanClass);
                cacheList.addAll(bean.getCacheList());
            }
        }

        return cacheList;
    }

    /**
     * 获取缓存实体
     *
     * @return
     */
    protected abstract List<BizCaffeineCache> getCacheList();


}
