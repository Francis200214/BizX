package com.biz.security.authentication;

import com.biz.security.authentication.type.AuthType;

/**
 * 认证方式工厂类，提供认证方式的实例化。
 * <p>
 * 根据不同的认证方式类型，提供对应的认证服务实例。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-13
 */
public class AuthenticationFactory {

    /**
     * 用户名密码认证方式。
     */
    private final UsernamePasswordAuthenticationService usernamePasswordAuthenticationService;

    /**
     * OAuth2 认证方式。
     */
    private final OAuth2AuthenticationService oAuth2AuthenticationService;

    /**
     * Token 认证方式。
     */
    private final TokenAuthenticationService tokenAuthenticationService;

    /**
     * 构造函数，注入各个认证方式的实例。
     *
     * @param usernamePasswordAuthenticationService 用户名密码认证方式实例
     * @param oAuth2AuthenticationService           OAuth2 认证方式实例
     * @param tokenAuthenticationService            Token 认证方式实例
     */
    public AuthenticationFactory(UsernamePasswordAuthenticationService usernamePasswordAuthenticationService, OAuth2AuthenticationService oAuth2AuthenticationService, TokenAuthenticationService tokenAuthenticationService) {
        this.usernamePasswordAuthenticationService = usernamePasswordAuthenticationService;
        this.oAuth2AuthenticationService = oAuth2AuthenticationService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    /**
     * 根据认证方式类型获取对应的认证服务实例。
     *
     * @param methodType 认证方式类型
     * @return {@link AuthenticationService} 认证服务实例
     * @throws IllegalArgumentException 如果不支持的认证方式类型
     */
    public AuthenticationService getAuthenticationService(AuthType methodType) {
        switch (methodType) {
            case USERNAME_PASSWORD:
                return usernamePasswordAuthenticationService;
            case OAUTH2:
                return oAuth2AuthenticationService;
            case TOKEN:
                return tokenAuthenticationService;
            default:
                throw new IllegalArgumentException("不支持的认证方式");
        }
    }

}