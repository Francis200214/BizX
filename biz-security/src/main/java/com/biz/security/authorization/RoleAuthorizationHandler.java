package com.biz.security.authorization;

import com.biz.security.authorization.enums.SecuredAccess;
import com.biz.security.user.UserDetails;

/**
 * 角色权限检查处理器。
 * <p>
 * 实现了 {@link AuthorizationHandler} 接口，用于检查用户是否具有访问指定角色的权限。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @create 2024-10-28
 * @since 1.0.1
 **/
public interface RoleAuthorizationHandler extends AuthorizationHandler {

    /**
     * 检查用户是否具有访问指定角色的权限。
     *
     * @param securedAccess   访问控制注解
     * @param userDetails 用户详情
     * @return 用户是否具有访问指定角色的权限
     */
    boolean authorizeResource(SecuredAccess securedAccess, UserDetails userDetails);

}
