package com.biz.security.authorization;

import com.biz.security.user.UserDetails;

/**
 * 权限处理器。
 *
 * <p>
 * 为不同的鉴权规则提供扩展点。支持按需定义多个处理器（如：基于资源的、基于角色的），并提供扩展性。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
public interface AuthorizationHandler {

    /**
     * 检查用户是否有权限访问资源。
     *
     * @param userDetails 用户信息
     * @param resource    资源名称
     * @return {@code true} 如果用户有权限访问资源，否则返回 {@code false}
     */
    boolean check(UserDetails userDetails, String resource);

}
