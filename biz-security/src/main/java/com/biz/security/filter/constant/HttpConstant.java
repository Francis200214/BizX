package com.biz.security.filter.constant;

/**
 * Http 常量类。
 *
 * <p>
 * 定义了常用的 HTTP 请求方法及请求头的常量。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
public final class HttpConstant {

    /**
     * POST 请求方式。
     */
    public static final String POST = "POST";

    /**
     * GET 请求方式。
     */
    public static final String GET = "GET";

    /**
     * PUT 请求方式。
     */
    public static final String PUT = "PUT";

    /**
     * DELETE 请求方式。
     */
    public static final String DELETE = "DELETE";

    /**
     * 请求头中的认证方式。
     */
    public static final String AUTH_TYPE = "X-Auth-Type";

    /**
     * 未知 IP 标识符。
     */
    public static final String UNKNOWN = "unknown";

    /**
     * 请求头中的 X-Forwarded-For。
     */
    public static final String FORWARDED_FOR = "X-Forwarded-For";

    /**
     * 请求头中的 X-Real-IP。
     */
    public static final String REAL_IP = "X-Real-IP";

    /**
     * 私有构造方法，防止实例化。
     */
    private HttpConstant() {
        throw new UnsupportedOperationException("不能实例化 HttpConstant 类");
    }
}
