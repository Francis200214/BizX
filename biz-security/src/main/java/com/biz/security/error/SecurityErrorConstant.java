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


    USER_OR_PASSWORD_FAILED(11001, "用户或密码错误"),
    USER_NOT_LOGIN(11002, "用户未登录"),
    USER_LOGIN_TIMEOUT(11003, "用户登录超时"),
    USER_LOGIN_EXPIRED(11004, "用户登录过期"),
    USER_LOGIN_LOCKED(11005, "用户登录锁定"),
    USER_LOGIN_DISABLED(11006, "用户登录禁用"),

    AUTHENTICATION_FAILED(12001, "认证时出现未知错误"),
    USERNAME_PASSWORD_AUTHENTICATION_FAILED(12002, "用户名密码认证失败"),
    O_AUTH_2_AUTHENTICATION_FAILED(12003, "oAuth2认证失败"),
    TOKEN_AUTHENTICATION_FAILED(12004, "token认证失败"),
    USERNAME_INFORMATION_IS_MISSING_FAILED(12005, "用户名信息缺失"),
    PASSWORD_INFORMATION_IS_MISSING_FAILED(12005, "密码信息缺失"),
    PASSWORD_INFORMATION_IS_MISSING_FAILED_IN_DATABASE(12005, "数据库中密码信息缺失"),
    O_AUTH_2_INFORMATION_IS_MISSING_FAILED(12006, "oAuth2认证信息缺失"),
    TOKEN_INFORMATION_IS_MISSING_FAILED(12007, "token认证信息缺失"),
    NONE_IMPLEMENTED_USERNAME_PASSWORD(12008, "未实现用户名密码方式获取用户信息"),
    NONE_IMPLEMENTED_TOKEN(12009, "未实现token方式获取用户信息"),
    NONE_IMPLEMENTED_O_AUTH_2(12010, "未实现OAuth2方式获取用户信息"),
    NOT_FOUND_AUTH_TYPE(12011, "未知的认证类型"),
    NOT_AUTH_TYPE(12012, "未传入认证类型"),
    REQUEST_TYPE_MUST_POST(12013, "请求类型必须为POST请求格式"),






    ;

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
