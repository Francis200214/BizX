package com.biz.security.filter;

import com.biz.security.authorization.AuthorizationManager;
import com.biz.security.authorization.enums.SecuredAccess;
import com.biz.security.error.AuthorizationException;
import com.biz.security.error.HaveNotResourceAuthorizationException;
import com.biz.security.filter.chain.FilterChain;
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
     * 授权管理器。
     */
    private final AuthorizationManager authorizationManager;

    /**
     * 构造函数。
     *
     * @param securityContextHolder 安全上下文持有者
     * @param authorizationManager  授权管理器
     */
    public ResourceAuthorizationFilter(SecurityContextHolder securityContextHolder, AuthorizationManager authorizationManager) {
        this.securityContextHolder = securityContextHolder;
        this.authorizationManager = authorizationManager;
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
        if (log.isDebugEnabled()) {
            log.debug("执行资源权限校验过滤");
        }

        try {
            Annotation annotation = HttpServletRequestUtils.getAnnotation(request, SecuredAccess.class);
            if (annotation != null) {
                SecuredAccess securedAccess = (SecuredAccess) annotation;
                if (!authorizationManager.authorizeResource(securedAccess, securityContextHolder.getContext())) {
                    if (log.isDebugEnabled()) {
                        log.debug("资源权限鉴权失败");
                    }
                    // 校验权限失败，抛出异常
                    throw new HaveNotResourceAuthorizationException();
                }
            }
        } catch (AuthorizationException e) {
            if (log.isDebugEnabled()) {
                log.debug("鉴权错误 {}", e.getMessage());
            }
            e.setResponse(response);
            return;

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("资源权限过滤器执行时未知错误 {}", e.getMessage());
            }
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "资源权限过滤器执行时未知错误");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(String.format("{\"code\":%d,\"message\":\"%s\"}", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "资源权限过滤器执行时未知错误"));
                return;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        // 权限校验通过，执行下一个过滤器
        chain.doFilter(request, response);
    }

}
