package com.biz.security.error;

import com.biz.common.error.BizXErrorConstant;

/**
 * Biz-Security 组件内异常常量类。
 *
 * <p>
 * 存储 Biz-Security 组件内的公共异常 Code 和 Message。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
public enum SecurityErrorConstant implements BizXErrorConstant {

    USER_NOT_FOUND(10001, "用户不存在"),
    USER_PASSWORD_ERROR(10002, "用户密码错误"),
    USER_NOT_LOGIN(10003, "用户未登录"),
    USER_LOGIN_TIMEOUT(10004, "用户登录超时"),
    USER_LOGIN_EXPIRED(10005, "用户登录过期"),
    USER_LOGIN_LOCKED(10006, "用户登录锁定"),
    USER_LOGIN_DISABLED(10007, "用户登录禁用"),

    AUTHENTICATION_FAILED(20001, "认证失败"),
    USERNAME_PASSWORD_AUTHENTICATION_FAILED(20002, "用户名密码认证失败"),
    O_AUTH_2_AUTHENTICATION_FAILED(20003, "oAuth2认证失败"),
    TOKEN_AUTHENTICATION_FAILED(20004, "token认证失败"),
    USERNAME_PASSWORD_INFORMATION_IS_MISSING_FAILED(20005, "用户名密码认证信息缺失"),
    O_AUTH_2_INFORMATION_IS_MISSING_FAILED(20006, "oAuth2认证信息缺失"),
    TOKEN_INFORMATION_IS_MISSING_FAILED(20007, "token认证信息缺失"),

    NONE_IMPLEMENTED_USERNAME_PASSWORD(30001, "未实现用户名密码方式获取用户信息"),
    NONE_IMPLEMENTED_TOKEN(30002, "未实现token方式获取用户信息"),
    NONE_IMPLEMENTED_O_AUTH_2(30003, "未实现OAuth2方式获取用户信息");

    /**
     * 异常代码。
     */
    private final int code;

    /**
     * 异常信息。
     */
    private final String message;

    /**
     * 构造函数。
     *
     * @param code    异常代码
     * @param message 异常信息
     */
    SecurityErrorConstant(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取异常代码。
     *
     * @return 异常代码
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * 获取异常信息。
     *
     * @return 异常信息
     */
    @Override
    public String getMessage() {
        return message;
    }
}
