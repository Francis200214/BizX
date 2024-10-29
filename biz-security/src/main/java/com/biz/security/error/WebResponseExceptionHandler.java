package com.biz.security.error;

import com.biz.common.error.BizXException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Web Security 异常
 *
 * @author francis
 * @create 2024-10-28
 * @since 1.0.1
 **/
@Slf4j
public abstract class WebResponseExceptionHandler extends BizXException implements WebException {

    /**
     * 设置返回状态码
     *
     * @param response 响应对象
     * @param code     状态码
     * @param message  错误信息
     */
    public void setResponse(HttpServletResponse response, int code, String message) {
        if (response == null) {
            throw new RuntimeException("响应对象为空");
        }

        try {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json;charset=UTF-8");
            response.sendError(getCode(), getMessage());
            response.getWriter().write(String.format("{\"code\":%d,\"message\":\"%s\"}", getCode(), getMessage()));
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("设置返回状态码时出现异常 {}", e.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置返回状态码
     *
     * @param response 响应对象
     */
    public void setResponse(HttpServletResponse response) {
        this.setResponse(response, getCode(), getMessage());
    }



}
