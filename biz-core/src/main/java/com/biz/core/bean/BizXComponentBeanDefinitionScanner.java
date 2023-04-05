package com.biz.core.bean;

import com.biz.library.bean.BizXComponent;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * @BizXComponent Bean注入实现类
 *
 * @author francis
 * @create: 2023-04-05 14:29
 **/
public class BizXComponentBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public BizXComponentBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    public void registerFilters() {
        addIncludeFilter(new AnnotationTypeFilter(BizXComponent.class));
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }

}
