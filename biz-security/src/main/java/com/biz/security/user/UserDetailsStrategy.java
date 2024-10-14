package com.biz.security.user;

/**
 * 用户信息获取策略接口。
 * <p>
 * 定义了用于根据不同方式（用户名、Token、OAuth2信息）获取用户详细信息的方法。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
public interface UserDetailsStrategy {

    /**
     * 根据用户名获取用户信息。
     *
     * @param username 用户名
     * @return {@link UserDetails} 用户详细信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 根据 Token 获取用户信息。
     *
     * @param token 用户的 Token
     * @return {@link UserDetails} 用户详细信息
     */
    UserDetails loadUserByToken(String token);

    /**
     * 根据 OAuth2 信息获取用户信息。
     *
     * @param provider OAuth2 认证提供商
     * @param oAuth2Info OAuth2 认证信息
     * @return {@link UserDetails} 用户详细信息
     */
    UserDetails loadUserByOAuth2(String provider, String oAuth2Info);

}