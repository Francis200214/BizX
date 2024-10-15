package com.biz.security.filter;

import com.biz.security.filter.chain.FilterChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义安全过滤器。
 *
 * <p>
 *      自定义过滤器接口，用于实现特定的安全过滤逻辑。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
public interface CustomSecurityFilter {

    /**
     * 执行过滤器。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param chain    过滤器链，用于传递请求给下一个过滤器
     */
    void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain);

}
