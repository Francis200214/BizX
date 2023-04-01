package com.biz.core;

import com.biz.core.interceptor.EnableCustomWebMvcRegisterServer;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
// 自定义WebMvc拦截类热插拔注解
@EnableCustomWebMvcRegisterServer
public @interface BizX {
}
