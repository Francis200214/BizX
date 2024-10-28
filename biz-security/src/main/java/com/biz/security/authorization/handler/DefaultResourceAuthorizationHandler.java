package com.biz.security.authorization.handler;

import com.biz.security.authorization.AuthorizationHandler;
import com.biz.security.authorization.ResourceAuthorizationHandler;
import com.biz.security.authorization.enums.SecuredAccess;
import com.biz.security.user.UserDetails;

/**
 * 资源权限检查处理器。
 * <p>
 * 实现了 {@link AuthorizationHandler} 接口，用于检查用户是否具有访问指定资源的权限。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
public class DefaultResourceAuthorizationHandler implements ResourceAuthorizationHandler {

    /**
     * 检查用户是否有权限访问资源。
     *
     * @param userDetails 用户信息
     * @param resource    资源名称
     * @return {@code true} 如果用户有权限访问资源，否则返回 {@code false}
     */
    @Override
    public boolean check(UserDetails userDetails, String resource) {
        return userDetails.getResources().contains(resource);
    }

    /**
     * 检查用户是否有权限访问资源。
     *
     * @param securedAccess 资源访问权限注解
     * @param userDetails   用户信息
     * @return {@code true} 如果用户有权限访问资源，否则返回 {@code false}
     */
    @Override
    public boolean authorizeResource(SecuredAccess securedAccess, UserDetails userDetails) {
        // 允许匿名访问
        if (securedAccess.allowAnonymous()) {
            return true;
        }
        // 允许不登录访问
        if (!securedAccess.requiresAuthentication()) {
            return true;
        }

        // 验证用户是否有权限访问
        for (String authority : securedAccess.hasAuthority()) {
            if (!this.check(userDetails, authority)) {
                return false;
            }
        }
        return true;
    }

}
