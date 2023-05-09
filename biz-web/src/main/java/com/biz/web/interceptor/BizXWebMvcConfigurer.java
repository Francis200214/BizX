package com.biz.web.interceptor;

import com.biz.web.interceptor.condition.AccessLimitConditionConfiguration;
import com.biz.web.interceptor.condition.CheckTokenConditionConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
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
        log.info("CustomWebMvcConfigurer addInterceptors");
        // 接口限时刷新
        registry.addInterceptor(accessLimitInterceptor())
                .addPathPatterns("/**");
        // 检查入参Token
        registry.addInterceptor(checkTokenHandlerInterceptor())
                .addPathPatterns("/**");
    }


    @Bean
    @Conditional(AccessLimitConditionConfiguration.class)
    public AccessLimitInterceptor accessLimitInterceptor() {
        return new AccessLimitInterceptor();
    }

    @Bean
    @Conditional(CheckTokenConditionConfiguration.class)
    public CheckTokenHandlerInterceptor checkTokenHandlerInterceptor() {
        return new CheckTokenHandlerInterceptor();
    }


}
