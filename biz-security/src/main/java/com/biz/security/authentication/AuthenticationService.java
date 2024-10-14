package com.biz.security.authentication;

import com.biz.security.error.AuthenticationException;
import com.biz.security.user.UserDetails;

/**
 * 认证服务接口。
 * <p>
 * 主要用于用户登录认证，返回用户身份信息，如用户名、角色、权限等。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-06
 */
public interface AuthenticationService {

    /**
     * 认证方法。
     *
     * @param loginRequest 登录请求参数
     * @return {@link UserDetails} 认证成功后返回的用户信息
     * @throws AuthenticationException 当认证失败时抛出异常
     */
    UserDetails authenticate(LoginRequest loginRequest) throws AuthenticationException;

}