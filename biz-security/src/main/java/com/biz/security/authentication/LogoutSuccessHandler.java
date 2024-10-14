package com.biz.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录成功处理接口。
 * <p>
 * 定义了当用户成功退出登录后的处理逻辑。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-11
 */
public interface LogoutSuccessHandler {

    /**
     * 退出登录成功处理。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @throws IOException 处理过程中可能抛出的 IO 异常
     */
    void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException;

}