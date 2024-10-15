package com.biz.security.filter.chain;

import com.biz.security.filter.SecurityFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * 过滤器链。
 *
 * <p>
 *     负责顺序调用多个过滤器并控制链条的执行。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-09
 */
public class FilterChain {

    /**
     * 过滤器列表。
     */
    private final Iterator<SecurityFilter> filterIterator;

    /**
     * 构造函数。
     *
     * @param filters 过滤器列表
     */
    public FilterChain(List<SecurityFilter> filters) {
        this.filterIterator = filters.iterator();
    }

    /**
     * 执行下一个过滤器。
     *
     * @param request  请求对象
     * @param response 响应对象
     */
    public void doFilter(HttpServletRequest request, HttpServletResponse response) {
        if (filterIterator.hasNext()) {
            SecurityFilter nextFilter = filterIterator.next();
            nextFilter.doFilter(request, response, this);
        }
    }
}
