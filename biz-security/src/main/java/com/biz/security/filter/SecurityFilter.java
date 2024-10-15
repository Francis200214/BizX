package com.biz.security.filter;

import com.biz.security.filter.chain.FilterChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤器接口。
 *
 * <p>
 * 定义过滤器的通用行为，允许过滤器链按顺序处理请求。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-09
 */
public interface SecurityFilter {

    /**
     * 执行过滤器操作。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param chain    过滤器链，用于传递请求给下一个过滤器
     */
    void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain);

}
