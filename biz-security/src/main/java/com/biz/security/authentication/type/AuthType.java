package com.biz.security.authentication.type;

/**
 * 认证类型。
 * <p>
 * 定义了系统支持的不同认证方式。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-13
 */
public enum AuthType {

    /**
     * 用户名密码认证。
     */
    USERNAME_PASSWORD,

    /**
     * OAuth2 认证。
     */
    OAUTH2,

    /**
     * Token 认证。
     */
    TOKEN;

}
