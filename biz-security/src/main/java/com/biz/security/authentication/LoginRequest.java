package com.biz.security.authentication;

import lombok.*;

import java.io.Serializable;

/**
 * LoginRequest 封装用户登录请求的数据。
 *
 * <p>
 *     根据不同的认证方式，可以包含不同的字段。
 *     此类是一个通用的请求对象，可以在不同认证方式中复用。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {

    /**
     * 用户名。
     */
    private String username;

    /**
     * 密码。
     */
    private String password;

    /**
     * 用户 Token。
     */
    private String token;

    /**
     * 第三方 OAuth2 服务提供商。
     */
    private String oAuth2Provider;

    /**
     * 用户名密码认证模式。
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link LoginRequest} 用户名密码认证请求对象
     */
    public static LoginRequest userNameAndPasswordAuthentication(String username, String password) {
        return new LoginRequest(username, password, null, null);
    }

    /**
     * Token 认证模式。
     *
     * @param token 用户 Token
     * @return {@link LoginRequest} Token 认证请求对象
     */
    public static LoginRequest tokenAuthentication(String token) {
        return new LoginRequest(null, null, token, null);
    }

    /**
     * OAuth2 认证模式。
     *
     * @param oAuth2Provider 第三方 OAuth2 服务提供商
     * @param token OAuth2 认证的 Token
     * @return {@link LoginRequest} OAuth2 认证请求对象
     */
    public static LoginRequest oAuth2Authentication(String oAuth2Provider, String token) {
        return new LoginRequest(null, null, token, oAuth2Provider);
    }

}