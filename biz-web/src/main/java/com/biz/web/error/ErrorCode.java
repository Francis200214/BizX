package com.biz.web.error;

/**
 * 系统异常
 *
 * @author francis
 * @create: 2023-05-08 18:14
 **/
public enum ErrorCode implements ErrorBaseInterface {
    /**
     * 成功
     */
    SUCCESS(200,"success"),
    /**
     * 未登录
     */
    NOT_LOGIN(1001,"未登录"),
    /**
     * 无权限
     */
    NO_AUTH(1002,"无权限"),
    /**
     * 系统内部未知异常
     */
    SYSTEM_ERROR(99999,"系统内部未知异常");


    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态码信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
