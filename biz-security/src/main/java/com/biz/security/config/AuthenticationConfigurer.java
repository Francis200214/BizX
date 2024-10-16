package com.biz.security.config;

import com.biz.security.authentication.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 认证配置。
 *
 * <p>
 * 配置用于 Biz-Security 组件的认证服务。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
@ConditionalOnProperty(prefix = "biz.security", name = "enabled", havingValue = "true")
public class AuthenticationConfigurer {

    /**
     * 默认退出成功处理器。
     *
     * @return DefaultLogoutSuccessHandler 实例
     */
    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler defaultLogoutSuccessHandler() {
        return new DefaultLogoutSuccessHandler();
    }

    /**
     * 用户名密码认证服务实现类。
     *
     * @return UsernamePasswordAuthenticationService 实例
     */
    @Bean
    public UsernamePasswordAuthenticationService usernamePasswordAuthenticationService() {
        return new UsernamePasswordAuthenticationService();
    }

    /**
     * Token 认证服务实现类。
     *
     * @return TokenAuthenticationService 实例
     */
    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return new TokenAuthenticationService();
    }

    /**
     * OAuth2 认证服务实现类。
     *
     * @return OAuth2AuthenticationService 实例
     */
    @Bean
    public OAuth2AuthenticationService oAuth2AuthenticationService() {
        return new OAuth2AuthenticationService();
    }

    /**
     * 认证工厂。
     *
     * @param usernamePasswordAuthenticationService 用户名密码认证服务实例
     * @param oAuth2AuthenticationService           OAuth2 认证服务实例
     * @param tokenAuthenticationService            Token 认证服务实例
     * @return AuthenticationFactory 实例
     */
    @Bean
    public AuthenticationFactory authenticationFactory(
            UsernamePasswordAuthenticationService usernamePasswordAuthenticationService,
            OAuth2AuthenticationService oAuth2AuthenticationService,
            TokenAuthenticationService tokenAuthenticationService) {
        return new AuthenticationFactory(usernamePasswordAuthenticationService, oAuth2AuthenticationService, tokenAuthenticationService);
    }
}
