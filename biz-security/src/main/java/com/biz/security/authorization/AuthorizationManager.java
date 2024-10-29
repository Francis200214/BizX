package com.biz.security.authorization;

import com.biz.security.authorization.enums.SecuredAccess;
import com.biz.security.error.NoneUserDetailsException;
import com.biz.security.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * 权限管理类，负责用户的权限校验逻辑。
 *
 * <p>
 * 负责用户权限校验的整体调度，将权限的具体判断逻辑交给多个 {@link AuthorizationHandler}。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-13
 */
@Slf4j
public class AuthorizationManager implements AuthorizationService {

    /**
     * 资源权限处理器。
     */
    private final ResourceAuthorizationHandler resourceAuthorizationHandler;

    /**
     * 角色权限处理器。
     */
    private final RoleAuthorizationHandler roleAuthorizationHandler;

    /**
     * 构造函数，注入资源权限处理器和角色权限处理器。
     *
     * @param resourceAuthorizationHandler 资源权限处理器
     * @param roleAuthorizationHandler     角色权限处理器
     */
    public AuthorizationManager(ResourceAuthorizationHandler resourceAuthorizationHandler, RoleAuthorizationHandler roleAuthorizationHandler) {
        this.resourceAuthorizationHandler = resourceAuthorizationHandler;
        this.roleAuthorizationHandler = roleAuthorizationHandler;
    }

    /**
     * 校验用户是否有权限访问资源。
     *
     * @param userDetails 用户信息
     * @param resource    资源名称
     * @return {@code true} 如果用户有权限访问资源，否则返回 {@code false}
     */
    @Override
    public boolean authorizeResource(UserDetails userDetails, String resource) {
        if (resource == null) {
            throw new NullPointerException("校验的资源名称不能为 Null");
        }
        return resourceAuthorizationHandler.check(userDetails, resource);
    }


    /**
     * 校验用户是否有权限访问资源。
     *
     * @param securedAccess 权限注解
     * @param userDetails   用户信息
     * @return {@code true} 如果用户有权限访问资源，否则返回 {@code false}
     */
    @Override
    public boolean authorizeResource(SecuredAccess securedAccess, UserDetails userDetails) {
        return resourceAuthorizationHandler.authorizeResource(securedAccess, userDetails);
    }

    /**
     * 校验用户是否有权限访问角色。
     *
     * @param userDetails 用户信息
     * @param role        角色名称
     * @return {@code true} 如果用户有权限访问角色，否则返回 {@code false}
     */
    @Override
    public boolean authorizeRole(UserDetails userDetails, String role) {
        if (role == null) {
            throw new NullPointerException("校验的角色名称不能为 Null");
        }

        return roleAuthorizationHandler.check(userDetails, role);
    }


    /**
     * 校验用户是否有权限访问角色。
     *
     * @param securedAccess 权限注解
     * @param userDetails   用户信息
     * @return {@code true} 如果用户有权限访问角色，否则返回 {@code false}
     */
    @Override
    public boolean authorizeRole(SecuredAccess securedAccess, UserDetails userDetails) {
        return roleAuthorizationHandler.authorizeResource(securedAccess, userDetails);
    }


}
