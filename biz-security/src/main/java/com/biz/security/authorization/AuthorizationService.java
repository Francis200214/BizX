package com.biz.security.authorization;

import com.biz.security.user.UserDetails;

/**
 * 鉴权服务。
 * <p>
 * 对用户访问特定资源进行权限校验，支持基于角色或权限的控制机制，确保用户只能够访问有权限的资源。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-06
 */
public interface AuthorizationService {

    /**
     * 校验用户是否有权限访问资源。
     *
     * @param userDetails 用户信息
     * @param resource    资源名称
     * @return {@code true} 如果用户有权限访问资源，否则返回 {@code false}
     */
    boolean authorize(UserDetails userDetails, String resource);

}
