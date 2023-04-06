package com.biz.core;

import com.biz.common.utils.ApplicationContextAwareBeanUtils;
import com.biz.core.spring.BizXSpringBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {
        ApplicationContextAwareBeanUtils.class,
        BizXSpringBeanDefinitionRegistrar.class
})
public @interface BizXEnable {
}
