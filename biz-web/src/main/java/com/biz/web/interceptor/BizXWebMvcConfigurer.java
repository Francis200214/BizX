package com.biz.web.interceptor;

import com.biz.library.bean.BizXComponent;
import lombok.extern.slf4j.Slf4j;
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
@BizXComponent
//@ConditionalOnBean(CustomWebMvcConfigurerKey.class)
public class BizXWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    @SuppressWarnings("all")
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("CustomWebMvcConfigurer addInterceptors");
        // 接口限时刷新
        registry.addInterceptor(accessLimintInterceptor())
                .addPathPatterns("/**");
        // 检查Token
        registry.addInterceptor(checkTokenHandlerInterceptor())
                .addPathPatterns("/**");
        // 检查入参参数是否符合规则
        registry.addInterceptor(checkParameterHandlerInterceptor())
                .addPathPatterns("/**");
    }


    @Bean
    public AccessLimintInterceptor accessLimintInterceptor() {
        return new AccessLimintInterceptor();
    }

    @Bean
    public CheckTokenHandlerInterceptor checkTokenHandlerInterceptor() {
        return new CheckTokenHandlerInterceptor();
    }

    @Bean
    public CheckParameterHandlerInterceptor checkParameterHandlerInterceptor() {
        return new CheckParameterHandlerInterceptor();
    }


}
