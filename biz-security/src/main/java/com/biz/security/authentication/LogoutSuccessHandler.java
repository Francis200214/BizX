package com.biz.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录成功返回值
 *
 * @author francis
 * @create 2024-10-11
 * @since 1.0.1
 **/
public interface LogoutSuccessHandler {

    /**
     * 退出登录成功处理
     *
     * @param request  请求值
     * @param response 响应值
     */
    void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
