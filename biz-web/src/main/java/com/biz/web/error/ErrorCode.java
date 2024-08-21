package com.biz.web.error;

/**
 * {@code ErrorCode} 枚举类定义了系统中常见的错误代码及其对应的错误消息。
 * <p>
 * 该枚举实现了 {@link ErrorBaseInterface} 接口，为每个错误代码提供了 `getCode()` 和 `getMessage()` 方法，
 * 用于获取错误代码和相应的错误描述。通过使用这个枚举，可以统一管理系统中的错误代码，提高代码的可读性和可维护性。
 *
 * <h3>错误代码列表:</h3>
 * <ul>
 *     <li>{@link #SUCCESS} - 操作成功，返回状态码 200。</li>
 *     <li>{@link #NOT_LOGIN} - 用户未登录，返回状态码 1001。</li>
 *     <li>{@link #NO_AUTH} - 用户无权限，返回状态码 1002。</li>
 *     <li>{@link #ACCOUNT_NOT_INIT} - 账户未初始化，返回状态码 1003。</li>
 *     <li>{@link #SYSTEM_ERROR} - 系统内部未知异常，返回状态码 99999。</li>
 * </ul>
 *
 * <h3>示例用法:</h3>
 * <pre>{@code
 * if (user == null) {
 *     throw new BizXWebException(ErrorCode.NOT_LOGIN);
 * }
 * }</pre>
 *
 * <p>通过这种方式，开发者可以在系统中统一使用这些错误代码，确保错误处理的一致性和清晰性。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 * @see ErrorBaseInterface
 */
public enum ErrorCode implements ErrorBaseInterface {
    /**
     * 成功
     */
    SUCCESS(200, "success"),

    /**
     * 未登录
     */
    NOT_LOGIN(1001, "未登录"),

    /**
     * 无权限
     */
    NO_AUTH(1002, "无权限"),

    /**
     * Account 没有初始化
     */
    ACCOUNT_NOT_INIT(1003, "Account not init"),

    /**
     * 系统内部未知异常
     */
    SYSTEM_ERROR(99999, "系统内部未知异常");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 枚举类型的构造函数，用于初始化错误代码和对应的错误消息。
     *
     * @param code    错误代码
     * @param message 错误消息
     */
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误代码。
     *
     * @return 错误代码
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * 获取错误消息。
     *
     * @return 错误消息
     */
    @Override
    public String getMessage() {
        return message;
    }
}
