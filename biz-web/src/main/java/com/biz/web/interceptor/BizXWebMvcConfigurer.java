package com.biz.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
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
public class BizXWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    @SuppressWarnings("all")
    public void addInterceptors(InterceptorRegistry registry) {
        // 接口限时刷新
        registry.addInterceptor(accessLimitInterceptor())
                .addPathPatterns("/**");
        // 检查Token
        registry.addInterceptor(checkTokenHandlerInterceptor())
                .addPathPatterns("/**");
        // 检查用户权限
        registry.addInterceptor(checkAuthorityInterceptor())
                .addPathPatterns("/**");
    }


    /**
     * 接口防刷
     *
     * @return AccessLimitInterceptor Bean
     */
    @Bean
    @ConditionalOnProperty(name = "biz.interceptor.access", havingValue = "true")
    public AccessLimitInterceptor accessLimitInterceptor() {
        return new AccessLimitInterceptor();
    }

    /**
     * 检查Token
     *
     * @return CheckTokenHandlerInterceptor Bean
     */
    @Bean
    @ConditionalOnProperty(name = "biz.interceptor.checkToken", havingValue = "true")
    public CheckTokenHandlerInterceptor checkTokenHandlerInterceptor() {
        return new CheckTokenHandlerInterceptor();
    }


    /**
     * 检查用户权限
     *
     * @return CheckAuthorityInterceptor Bean
     */
    @Bean
    @ConditionalOnProperty(name = "biz.interceptor.auth",  havingValue = "true")
    public CheckAuthorityInterceptor checkAuthorityInterceptor() {
        return new CheckAuthorityInterceptor();
    }


}
