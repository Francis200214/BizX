package com.biz.security.error;

import javax.servlet.http.HttpServletResponse;

/**
 * Web 异常
 *
 * @author francis
 * @create 2024-10-28
 * @since 1.0.1
 **/
public interface WebException {


    /**
     * 设置异常响应值
     *
     * @param response 响应
     * @param code     异常码
     * @param message  异常信息
     */
    void setResponse(HttpServletResponse response, int code, String message);

    /**
     * 设置异常响应值
     *
     * @param response 响应
     */
    void setResponse(HttpServletResponse response);

}
