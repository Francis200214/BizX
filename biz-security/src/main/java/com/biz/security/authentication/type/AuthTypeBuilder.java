package com.biz.security.authentication.type;

import com.biz.common.utils.Common;
import com.biz.security.error.AuthenticationException;
import com.biz.security.error.SecurityErrorConstant;
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
     * @throws AuthenticationException 如果认证类型转换时出现错误则抛出 {@link AuthenticationException} 异常
     */
    public static AuthType getAuthType(String authType) throws AuthenticationException {
        if (Common.isBlank(authType)) {
            if (log.isDebugEnabled()) {
                log.debug("Header 中未传入认证类型数据");
            }
            throw new AuthenticationException(SecurityErrorConstant.NOT_AUTH_TYPE);
        }

        try {
            return AuthType.valueOf(authType);

        } catch (IllegalArgumentException e) {
            if (log.isDebugEnabled()) {
                log.debug("未知的认证类型! 未知的认证类型为 {}", authType);
            }
            throw new AuthenticationException(SecurityErrorConstant.NOT_FOUND_AUTH_TYPE);

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("认证类型转换时出现了未知错误 {}", e.getMessage());
            }
            throw new AuthenticationException();
        }
    }

}
