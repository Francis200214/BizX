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
 * @create 2024-09-20
 * @since 1.0.1
 **/
public interface AuthorizationHandler {


    /**
     * 检查用户是否有权限访问资源
     *
     * @param userDetails 用户信息
     * @param resource    资源名称
     * @return true: 有权限访问, false: 无权限访问
     */
    boolean check(UserDetails userDetails, String resource);

}
