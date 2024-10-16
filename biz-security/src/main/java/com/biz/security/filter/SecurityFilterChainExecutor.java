package com.biz.security.filter;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.security.filter.chain.FilterChain;
import com.biz.security.filter.chain.FilterChainBuilder;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 过滤器链的入口。
 *
 * <p>
 * 将所有过滤器串联并执行。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-09
 */
@WebFilter(urlPatterns = "/*")
public final class SecurityFilterChainExecutor implements SmartInitializingSingleton, Filter {

    /**
     * 认证校验过滤器。
     */
    private AuthenticationFilter authenticationFilter;

    /**
     * 请求头中真实 IP 地址过滤器。
     */
    private RealIpUpdateFilter forwardedHeaderFilter;

    /**
     * 登出过滤器。
     */
    private LogoutFilter logoutFilter;

    /**
     * 角色校验过滤器。
     */
    private RoleAuthorizationFilter roleAuthorizationFilter;

    /**
     * 资源校验过滤器。
     */
    private ResourceAuthorizationFilter resourceAuthorizationFilter;

    /**
     * 自定义过滤器。
     */
    private List<CustomSecurityFilter> customFilters;

    /**
     * 执行过滤器操作。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param chain    过滤器链
     * @throws IOException      可能抛出的 IO 异常
     * @throws ServletException 可能抛出的 Servlet 异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, javax.servlet.FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // 执行过滤器链
            execute(httpRequest, httpResponse);
            if (httpResponse.getStatus() != 200) {
                return;
            }
        }

        // 将请求传递给下一个过滤器
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 销毁操作
    }

    /**
     * 初始化过滤器链中的各个过滤器。
     */
    @Override
    public void afterSingletonsInstantiated() {
        this.authenticationFilter = BizXBeanUtils.getBean(AuthenticationFilter.class);
        this.forwardedHeaderFilter = BizXBeanUtils.getBean(RealIpUpdateFilter.class);
        this.logoutFilter = BizXBeanUtils.getBean(LogoutFilter.class);
        this.roleAuthorizationFilter = BizXBeanUtils.getBean(RoleAuthorizationFilter.class);
        this.resourceAuthorizationFilter = BizXBeanUtils.getBean(ResourceAuthorizationFilter.class);
        Map<String, CustomSecurityFilter> beansOfType = BizXBeanUtils.getBeansOfType(CustomSecurityFilter.class);
        this.customFilters = new ArrayList<>(beansOfType.values());
        // 对自定义过滤器列表按 @Order 注解排序
        AnnotationAwareOrderComparator.sort(customFilters);
    }

    /**
     * 执行过滤器链。
     *
     * @param request  请求
     * @param response 响应
     */
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        // 构建过滤器链
        FilterChainBuilder builder = new FilterChainBuilder()
                // 内置过滤器
                // 认证（同时设置用户信息）
                .addFilter(authenticationFilter)
                // 请求头中真实IP地址
                .addFilter(forwardedHeaderFilter)
                // 退出登录
                .addFilter(logoutFilter)
                // 角色校验
                .addFilter(roleAuthorizationFilter)
                // 资源校验
                .addFilter(resourceAuthorizationFilter);

        // 加入自定义过滤器
        for (CustomSecurityFilter customFilter : customFilters) {
            builder.addFilter(customFilter::doFilter);
        }

        // 自定义过滤器执行
        FilterChain chain = builder.build();
        // 执行过滤器链
        chain.doFilter(request, response);
    }

}
