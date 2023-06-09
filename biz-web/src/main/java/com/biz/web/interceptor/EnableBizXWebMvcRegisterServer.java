package com.biz.web.interceptor;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 控制 WebMvcConfigurer 热插拔注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(BizXWebMvcConfigurer.class)
public @interface EnableBizXWebMvcRegisterServer {
}
