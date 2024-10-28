package com.biz.security.error;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 角色鉴权异常
 *
 * @author francis
 * @create 2024-10-28
 * @since 1.0.1
 **/
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class HaveNotRoleAuthorizationException extends AuthorizationException {

    /**
     * 异常 Code 码。
     */
    private int CODE = SecurityErrorConstant.HAVE_NOT_ROLE_AUTHORIZATION_FAILED.getCode();

    /**
     * 异常信息。
     */
    private String MESSAGE = SecurityErrorConstant.HAVE_NOT_ROLE_AUTHORIZATION_FAILED.getMessage();

    /**
     * 构造方法，根据错误常量创建异常实例。
     *
     * @param errorConstant 错误常量
     */
    public HaveNotRoleAuthorizationException(SecurityErrorConstant errorConstant) {
        this.CODE = errorConstant.getCode();
        this.MESSAGE = errorConstant.getMessage();
    }

    @Override
    public int getCode() {
        return this.CODE;
    }

    @Override
    public String getMessage() {
        return this.MESSAGE;
    }
}
