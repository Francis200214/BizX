package com.biz.security.user;

import com.biz.security.error.NoneUserDetailsException;
import com.biz.security.error.SecurityErrorConstant;

/**
 * 默认用户信息获取策略实现类。
 * <p>
 * 此类提供了 {@link UserDetailsStrategy} 的默认实现，
 * 所有方法在未被重写时都会抛出 {@link NoneUserDetailsException} 异常，表示未实现相应的功能。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-11
 */
public class DefaultUserDetailsStrategy implements UserDetailsStrategy {

    /**
     * 根据用户名获取用户信息。
     *
     * @param username 用户名
     * @return {@link UserDetails} 用户详细信息
     * @throws NoneUserDetailsException 如果未实现此方法
     */
    public UserDetails loadUserByUsername(String username) {
        throw new NoneUserDetailsException(SecurityErrorConstant.NONE_IMPLEMENTED_USERNAME_PASSWORD);
    }

    /**
     * 根据 Token 获取用户信息。
     *
     * @param token 用户的 Token
     * @return {@link UserDetails} 用户详细信息
     * @throws NoneUserDetailsException 如果未实现此方法
     */
    public UserDetails loadUserByToken(String token) {
        throw new NoneUserDetailsException(SecurityErrorConstant.NONE_IMPLEMENTED_TOKEN);
    }

    /**
     * 根据 OAuth2 信息获取用户信息。
     *
     * @param provider OAuth2 认证提供商
     * @param oAuth2Info OAuth2 认证信息
     * @return {@link UserDetails} 用户详细信息
     * @throws NoneUserDetailsException 如果未实现此方法
     */
    public UserDetails loadUserByOAuth2(String provider, String oAuth2Info) {
        throw new NoneUserDetailsException(SecurityErrorConstant.NONE_IMPLEMENTED_O_AUTH_2);
    }

}