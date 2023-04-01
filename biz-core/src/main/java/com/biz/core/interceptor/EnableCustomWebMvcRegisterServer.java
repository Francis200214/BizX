package com.biz.core.interceptor;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 控制 CustomWebMvcConfigurer 热插拔注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomWebMvcConfigurerKey.class)
public @interface EnableCustomWebMvcRegisterServer {
}
