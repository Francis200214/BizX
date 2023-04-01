package com.biz.core.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义 Web Mvc 拦截器控制类
 * 主要控制打开关闭各项业务的拦截器
 *
 * @author francis
 * @create 2023/4/1 16:30
 */
@Slf4j
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("CustomWebMvcConfigurer addInterceptors");
        registry.addInterceptor(customCheckTokenHandlerInterceptor())
                // 拦截所有请求
                .addPathPatterns("/**");
    }


    @Bean
    public CustomCheckTokenHandlerInterceptor customCheckTokenHandlerInterceptor() {
        return new CustomCheckTokenHandlerInterceptor();
    }


}
