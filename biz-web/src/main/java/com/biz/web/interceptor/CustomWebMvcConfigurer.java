package com.biz.web.interceptor;

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
//@ConditionalOnBean(CustomWebMvcConfigurerKey.class)
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("CustomWebMvcConfigurer addInterceptors");
        // 检查Token
        registry.addInterceptor(customCheckTokenHandlerInterceptor())
                .addPathPatterns("/**");
        // 检查入参参数是否符合规则
        registry.addInterceptor(customCheckParameterHandlerInterceptor())
                .addPathPatterns("/**");
    }


    @Bean
    public CustomCheckTokenHandlerInterceptor customCheckTokenHandlerInterceptor() {
        return new CustomCheckTokenHandlerInterceptor();
    }

    @Bean
    public CustomCheckParameterHandlerInterceptor customCheckParameterHandlerInterceptor() {
        return new CustomCheckParameterHandlerInterceptor();
    }


}
