package com.biz.security.filter.chain;

import com.biz.security.filter.SecurityFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器链构建器。
 *
 * <p>
 *     提供流式 API 用于构建过滤器链。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-09
 */
public class FilterChainBuilder {

    /**
     * 过滤器列表。
     */
    private final List<SecurityFilter> filters = new ArrayList<>();

    /**
     * 添加过滤器。
     *
     * @param filter 要添加的过滤器
     * @return 返回当前构建器实例
     */
    public FilterChainBuilder addFilter(SecurityFilter filter) {
        filters.add(filter);
        return this;
    }

    /**
     * 构建过滤器链。
     *
     * @return 返回构建的过滤器链
     */
    public FilterChain build() {
        return new FilterChain(filters);
    }
}
