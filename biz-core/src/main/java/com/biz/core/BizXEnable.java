package com.biz.core;

import com.biz.common.utils.ApplicationContextAwareBeanUtils;
import com.biz.core.interceptor.EnableCustomWebMvcRegisterServer;
import com.biz.core.spring.BizXSpringBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
// 自定义WebMvc拦截类热插拔注解
@EnableCustomWebMvcRegisterServer
@Import(value = {
        ApplicationContextAwareBeanUtils.class,
        BizXSpringBeanDefinitionRegistrar.class
})
public @interface BizXEnable {
}
