package com.biz.security.authentication;

import com.biz.security.authentication.type.AuthType;

/**
 * 认证方式工厂类，提供认证方式的实例化
 *
 * @author francis
 * @create 2024-09-13
 * @since 1.0.1
 **/
public class AuthenticationFactory {

    /**
     * 用户名密码认证方式
     */
    private final UsernamePasswordAuthenticationService usernamePasswordAuthenticationService;

    /**
     * OAuth2认证方式
     */
    private final OAuth2AuthenticationService oAuth2AuthenticationService;

    /**
     * Token认证方式
     */
    private final TokenAuthenticationService tokenAuthenticationService;


    /**
     * 构造函数，注入各个认证方式的实例
     *
     * @param usernamePasswordAuthenticationService 用户名密码认证方式实例
     * @param oAuth2AuthenticationService           OAuth2认证方式实例
     * @param tokenAuthenticationService            Token认证方式实例
     */
    public AuthenticationFactory(UsernamePasswordAuthenticationService usernamePasswordAuthenticationService, OAuth2AuthenticationService oAuth2AuthenticationService, TokenAuthenticationService tokenAuthenticationService) {
        this.usernamePasswordAuthenticationService = usernamePasswordAuthenticationService;
        this.oAuth2AuthenticationService = oAuth2AuthenticationService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }


    /**
     * 根据认证方式类型获取对应的认证服务实例
     *
     * @param methodType 认证方式类型
     * @return 认证服务实例
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
