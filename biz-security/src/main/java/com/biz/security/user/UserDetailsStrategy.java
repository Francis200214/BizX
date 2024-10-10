package com.biz.security.user;

import com.biz.security.error.NoneUserDetailsException;
import com.biz.security.error.SecurityErrorConstant;

/**
 * 获取用户信息
 *
 * @author francis
 * @create 2024-09-20
 * @since 1.0.1
 **/
public interface UserDetailsStrategy {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return UserDetails 用户详细信息
     */
    default UserDetails loadUserByUsername(String username) {
        throw new NoneUserDetailsException(SecurityErrorConstant.NONE_IMPLEMENTED_USERNAME_PASSWORD);
    }

    /**
     * 根据Token获取用户信息
     *
     * @param token 用户的Token
     * @return UserDetails 用户详细信息
     */
    default UserDetails loadUserByToken(String token) {
        throw new NoneUserDetailsException(SecurityErrorConstant.NONE_IMPLEMENTED_TOKEN);
    }

    /**
     * 根据OAuth2信息获取用户信息
     *
     * @param oAuth2Info OAuth2认证信息
     * @return UserDetails 用户详细信息
     */
    default UserDetails loadUserByOAuth2(String oAuth2Info) {
        throw new NoneUserDetailsException(SecurityErrorConstant.NONE_IMPLEMENTED_O_AUTH_2);
    }

}
