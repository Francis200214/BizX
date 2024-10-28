package com.biz.security.authorization;

import com.biz.security.authorization.enums.SecuredAccess;
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
    boolean authorizeResource(UserDetails userDetails, String resource);

    /**
     * 校验用户是否有权限访问资源。
     *
     * @param securedAccess 资源访问控制信息
     * @param userDetails   用户信息
     * @return {@code true} 如果用户有权限访问资源，否则返回 {@code false}
     */
    boolean authorizeResource(SecuredAccess securedAccess, UserDetails userDetails);

    /**
     * 校验用户是否有指定角色。
     *
     * @param userDetails 用户信息
     * @param role        角色名称
     * @return {@code true} 如果用户有指定角色，否则返回 {@code false}
     */
    boolean authorizeRole(UserDetails userDetails, String role);

    /**
     * 校验用户是否有指定角色。
     *
     * @param securedAccess 资源访问控制信息
     * @param userDetails   用户信息
     * @return {@code true} 如果用户有指定角色，否则返回 {@code false}
     */
    boolean authorizeRole(SecuredAccess securedAccess, UserDetails userDetails);

}
