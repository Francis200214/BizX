package com.biz.security.authorization.handler;

import com.biz.security.authorization.AuthorizationHandler;
import com.biz.security.user.UserDetails;

/**
 * 角色权限检查处理器。
 * <p>
 * 实现了 {@link AuthorizationHandler} 接口，用于检查用户是否具有访问指定角色的权限。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
public class RoleAuthorizationHandler implements AuthorizationHandler {

    /**
     * 检查用户是否有权限访问角色。
     *
     * @param userDetails 用户信息
     * @param role        角色名称
     * @return {@code true} 如果用户具有该角色权限，否则返回 {@code false}
     */
    @Override
    public boolean check(UserDetails userDetails, String role) {
        return userDetails.getRoles().contains(role);
    }
}
