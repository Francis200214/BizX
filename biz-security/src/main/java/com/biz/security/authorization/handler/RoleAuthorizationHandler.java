package com.biz.security.authorization.handler;

import com.biz.security.authorization.AuthorizationHandler;
import com.biz.security.user.UserDetails;

/**
 * 角色检查。
 *
 * @author francis
 * @create 2024-09-20
 * @since 1.0.1
 **/
public class RoleAuthorizationHandler implements AuthorizationHandler {

    @Override
    public boolean check(UserDetails userDetails, String role) {
        return userDetails.getRoles().contains(role);
    }

}
