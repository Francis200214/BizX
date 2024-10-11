package com.biz.security.authentication.type;

import com.biz.common.utils.Common;

/**
 * 认证类型 Builder
 *
 * @author francis
 * @create 2024-10-10
 * @since 1.0.1
 **/
public class AuthTypeBuilder {

    /**
     * 获取认证类型
     *
     * @param authType 认证类型
     * @return 认证类型
     */
    public static AuthType getAuthType(String authType) {
        if (Common.isBlank(authType)) {
            throw new IllegalArgumentException("认证类型不能为空");
        }
        return AuthType.valueOf(authType);
    }

}
