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
 * 资源权限校验过滤器。
 *
 * <p>
 * 用于校验用户对特定资源的访问权限。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
@Slf4j
public final class ResourceAuthorizationFilter implements SecurityFilter {

    /**
     * 本次请求的用户信息。
     */
    private final SecurityContextHolder securityContextHolder;

    /**
     * 构造函数。
     *
     * @param securityContextHolder 安全上下文持有者
     */
    public ResourceAuthorizationFilter(SecurityContextHolder securityContextHolder) {
        this.securityContextHolder = securityContextHolder;
    }

    /**
     * 执行资源权限校验过滤。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param chain    过滤器链
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        log.debug("执行资源权限校验过滤...");
        Annotation annotation = HttpServletRequestUtils.getAnnotation(request, SecuredAccess.class);
        if (annotation != null) {
            SecuredAccess securedAccess = (SecuredAccess) annotation;
            if (!validateResourceAccess(securedAccess, securityContextHolder.getContext())) {
                // 校验权限失败，返回错误信息
                handleAccessDenied(response);
                // 终止过滤器链
                return;
            }
        }

        // 权限校验通过，执行下一个过滤器
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
            response.getWriter().write("{\"error\": \"Access Denied\", \"message\": \"You do not have permission to access this resource.\"}");
        } catch (IOException e) {
            throw new RuntimeException("权限校验失败时设置 Response 出错", e);
        }
    }

    /**
     * 校验资源访问权限。
     *
     * @param securedAccess 资源访问注解
     * @param userDetails   用户信息
     * @return {@code true} 如果用户有权限访问资源，否则返回 {@code false}
     */
    private static boolean validateResourceAccess(SecuredAccess securedAccess, UserDetails userDetails) {
        // 允许匿名访问
        if (securedAccess.allowAnonymous()) {
            return true;
        }
        // 允许不登录访问
        if (!securedAccess.requiresAuthentication()) {
            return true;
        }

        // 验证用户是否登录
        if (securedAccess.hasAuthority().length == 0) {
            return true;
        }

        // 验证用户是否有权限访问
        for (String authority : securedAccess.hasAuthority()) {
            for (String resource : userDetails.getResources()) {
                if (authority.equals(resource)) {
                    return true;
                }
            }
        }

        return false;
    }
}
