package com.biz.core.bean;

import com.biz.core.annotaion.BizXComponent;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * @author francis
 * @BizXComponent Bean注入实现类
 * @since 1.0.1
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
