package com.biz.security.filter;

import com.biz.security.authorization.enums.SecuredAccess;
import com.biz.security.filter.chain.FilterChain;
import com.biz.security.user.UserDetails;
import com.biz.security.user.store.SecurityContextHolder;
import com.biz.security.util.HttpServletRequestUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * 角色权限校验过滤器。
 *
 * <p>
 *     用于检查用户是否具有访问资源的角色权限。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-09
 */
@Slf4j
public final class RoleAuthorizationFilter implements SecurityFilter {

    /**
     * 本次请求的用户信息。
     */
    private final SecurityContextHolder securityContextHolder;

    /**
     * 构造函数。
     *
     * @param securityContextHolder 安全上下文持有者
     */
    public RoleAuthorizationFilter(SecurityContextHolder securityContextHolder) {
        this.securityContextHolder = securityContextHolder;
    }

    /**
     * 执行角色权限校验过滤。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param chain    过滤器链
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        log.debug("角色权限过滤器执行中...");
        Annotation annotation = HttpServletRequestUtils.getAnnotation(request, SecuredAccess.class);
        if (annotation != null) {
            SecuredAccess securedAccess = (SecuredAccess) annotation;
            if (!validateRoleAccess(securedAccess, securityContextHolder.getContext())) {
                // 校验角色失败，返回错误信息
                handleAccessDenied(response);
                // 终止过滤器链
                return;
            }
        }
        // 角色权限校验通过，执行下一个过滤器
        chain.doFilter(request, response);
    }

    /**
     * 处理访问被拒绝的情况。
     *
     * @param response 响应对象
     */
    private void handleAccessDenied(HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 设置状态码为 403 Forbidden
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Access Denied\", \"message\": \"You do not have permission to access this role.\"}");
        } catch (IOException e) {
            throw new RuntimeException("角色权限校验失败时设置 Response 出错", e);
        }
    }

    /**
     * 校验角色访问权限。
     *
     * @param securedAccess 角色访问注解
     * @param userDetails   用户信息
     * @return {@code true} 如果用户有权限访问角色，否则返回 {@code false}
     */
    private static boolean validateRoleAccess(SecuredAccess securedAccess, UserDetails userDetails) {
        // 允许匿名访问
        if (securedAccess.allowAnonymous()) {
            return true;
        }
        // 允许不登录访问
        if (!securedAccess.requiresAuthentication()) {
            return true;
        }

        // 验证用户是否登录
        if (securedAccess.hasRole().length == 0) {
            return true;
        }

        // 验证用户是否有角色权限访问
        for (String roleAccess : securedAccess.hasRole()) {
            for (String role : userDetails.getRoles()) {
                if (roleAccess.equals(role)) {
                    return true;
                }
            }
        }

        return false;
    }
}
