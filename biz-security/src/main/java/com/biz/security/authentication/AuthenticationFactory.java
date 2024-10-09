package com.biz.security.authentication;

/**
 * 认证方式工厂类，提供认证方式的实例化
 *
 * @author francis
 * @create 2024-09-13
 * @since 1.0.1
 **/
public class AuthenticationFactory {

    public static AuthenticationService getAuthenticationService(AuthMethodType methodType) {
        switch (methodType) {
            case USERNAME_PASSWORD:
                return new UsernamePasswordAuthenticationService();
            case OAUTH2:
                return new OAuth2AuthenticationService();
            case TOKEN:
                return new TokenAuthenticationService();
            default:
                throw new IllegalArgumentException("不支持的认证方式");
        }
    }

}
