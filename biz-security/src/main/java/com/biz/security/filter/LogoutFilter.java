package com.biz.security.filter;

import com.biz.security.authentication.LogoutSuccessHandler;
import com.biz.security.filter.chain.FilterChain;
import com.biz.security.user.store.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录过滤器。
 *
 * <p>
 * 用于处理用户的退出登录逻辑。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-11
 */
@Slf4j
public class LogoutFilter implements SecurityFilter {

    /**
     * 安全上下文持有者。
     */
    private final SecurityContextHolder securityContextHolder;

    /**
     * 退出登录成功处理器。
     */
    private final LogoutSuccessHandler logoutSuccessHandler;

    /**
     * 构造方法。
     *
     * @param securityContextHolder 安全上下文持有者
     * @param logoutSuccessHandler  退出登录成功处理器
     */
    public LogoutFilter(SecurityContextHolder securityContextHolder, LogoutSuccessHandler logoutSuccessHandler) {
        this.securityContextHolder = securityContextHolder;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    /**
     * 执行退出登录过滤。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param chain    过滤器链
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        log.debug("退出登录过滤器执行");
        // 检查是否是退出模式
        if (this.isLogoutRequest(request)) {
            // 执行下一个过滤器
            chain.doFilter(request, response);
        }

        securityContextHolder.clearContext();
        try {
            logoutSuccessHandler.onLogoutSuccess(request, response);
        } catch (IOException e) {
            throw new RuntimeException("退出登录处理过程中出现异常", e);
        }
        // 处理登录完成，执行下一个过滤器
        chain.doFilter(request, response);
    }

    /**
     * 检查是否是退出登录请求。
     *
     * @param request 请求对象
     * @return 是否是退出登录请求
     */
    private boolean isLogoutRequest(HttpServletRequest request) {
        return request.getRequestURI().endsWith("/logout");
    }

}
