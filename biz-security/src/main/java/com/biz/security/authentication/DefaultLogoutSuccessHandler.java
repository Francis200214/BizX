package com.biz.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认退出登录成功处理类。
 * <p>
 * 实现了 {@link LogoutSuccessHandler} 接口，定义了用户成功退出登录后的默认响应逻辑。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-11
 */
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 退出登录成功处理。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @throws IOException 处理过程中可能抛出的 IO 异常
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"退出登录成功\"}");
    }
}
