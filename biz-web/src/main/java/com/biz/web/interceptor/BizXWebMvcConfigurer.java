package com.biz.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义 Web Mvc 拦截器控制类
 * 主要控制打开关闭各项业务的拦截器
 *
 * @author francis
 * @since 1.0.1
 */
@Slf4j
public class BizXWebMvcConfigurer implements WebMvcConfigurer {


    private Environment env;

    public BizXWebMvcConfigurer(Environment env) {
        this.env = env;
    }

    @Override
    @SuppressWarnings("all")
    public void addInterceptors(InterceptorRegistry registry) {

        if (env.getProperty("biz.interceptor.access", Boolean.class, false)) {
            registry.addInterceptor(accessLimitInterceptor())
                    .addPathPatterns("/**");
        }

        if (env.getProperty("biz.interceptor.checkToken", Boolean.class, false)) {
            registry.addInterceptor(checkTokenHandlerInterceptor())
                    .addPathPatterns("/**");
        }

        if (env.getProperty("biz.interceptor.auth", Boolean.class, false)) {
            registry.addInterceptor(checkAuthorityInterceptor())
                    .addPathPatterns("/**");
        }

    }


    /**
     * 接口防刷
     *
     * @return AccessLimitInterceptor Bean
     */
    @Bean
    public AccessLimitInterceptor accessLimitInterceptor() {
        return new AccessLimitInterceptor();
    }

    /**
     * 检查Token
     *
     * @return CheckTokenHandlerInterceptor Bean
     */
    @Bean
    public CheckTokenHandlerInterceptor checkTokenHandlerInterceptor() {
        return new CheckTokenHandlerInterceptor();
    }


    /**
     * 检查用户权限
     *
     * @return CheckAuthorityInterceptor Bean
     */
    @Bean
    public CheckAuthorityInterceptor checkAuthorityInterceptor() {
        return new CheckAuthorityInterceptor();
    }


}
