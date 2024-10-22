package com.biz.security.authentication.type;

import com.biz.common.utils.Common;
import com.biz.security.error.AuthenticationTypeConvertException;
import lombok.extern.slf4j.Slf4j;

/**
 * 认证类型 Builder。
 * <p>
 * 提供根据字符串获取认证类型的功能。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
@Slf4j
public class AuthTypeBuilder {

    /**
     * 获取认证类型。
     *
     * @param authType 认证类型字符串
     * @return {@link AuthType} 对应的认证类型枚举
     * @throws IllegalArgumentException 如果认证类型为空或无效
     */
    public static AuthType getAuthType(String authType) {
        if (Common.isBlank(authType)) {
            return null;
        }

        try {
            return AuthType.valueOf(authType);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("未知的认证类型! 未知的认证类型为 {}", authType);
            }
            throw new AuthenticationTypeConvertException();
        }
    }

}
