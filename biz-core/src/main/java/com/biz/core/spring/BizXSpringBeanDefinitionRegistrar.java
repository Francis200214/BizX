package com.biz.core.spring;

import com.biz.library.bean.BizXComponent;
import com.biz.core.bean.BizXComponentBeanDefinitionScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;


/**
 * Spring bean definition
 *
 * @author francis
 * @create: 2023-04-05 13:46
 **/
public class BizXSpringBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    public BizXSpringBeanDefinitionRegistrar() {
        // 打印 Banner
        printBanner();
    }



    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 注入带有 @BizXComponent 的 Class
        this.injectBizXComponentBeanDefinitionScanner(registry);


    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 注入 @BizXComponent 注解实现
     * @param registry
     */
    private void injectBizXComponentBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        BizXComponentBeanDefinitionScanner scanner = new BizXComponentBeanDefinitionScanner(registry, false);
        scanner.setResourceLoader(resourceLoader);
        scanner.registerFilters();
        scanner.addIncludeFilter(new AnnotationTypeFilter(BizXComponent.class));
        scanner.doScan("com.biz");
    }

    /**
     * 打印 Banner 图
     */
    private static void printBanner() {
        System.out.println("\n" +
                "  ____  _    __  __\n" +
                " | __ )(_)___\\ \\/ /\n" +
                " |  _ \\| |_  /\\  / \n" +
                " | |_) | |/ / /  \\ \n" +
                " |____/|_/___/_/\\_\\\n" +
                "                   \n");
    }

}
