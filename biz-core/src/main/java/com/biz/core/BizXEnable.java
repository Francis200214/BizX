package com.biz.core;

import com.biz.common.utils.ApplicationContextAwareUtils;
import com.biz.core.spring.BizXSpringBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {
//        ApplicationContextAwareUtils.class,
        BizXSpringBeanDefinitionRegistrar.class
})
public @interface BizXEnable {
}
