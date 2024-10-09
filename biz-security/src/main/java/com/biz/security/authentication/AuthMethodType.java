package com.biz.security.authentication;

/**
 * 认证类型
 *
 * @author francis
 * @create 2024-09-13
 * @since 1.0.1
 **/
public enum AuthMethodType {

    /**
     * 用户名密码认证
     */
    USERNAME_PASSWORD,
    /**
     * OAuth2认证
     */
    OAUTH2,
    /**
     * Token认证
     */
    TOKEN;

}
