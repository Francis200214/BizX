package com.biz.security.config;

import com.biz.security.authentication.AuthenticationFactory;
import com.biz.security.authentication.LogoutSuccessHandler;
import com.biz.security.filter.*;
import com.biz.security.user.store.SecurityContextHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 过滤器配置。
 *
 * <p>
 * 配置用于 Biz-Security 组件的过滤器。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
@ConditionalOnProperty(prefix = "biz.security", name = "enabled", havingValue = "true")
public class FilterConfigurer {

    /**
     * 认证过滤器。
     *
     * @param authenticationFactory 认证工厂
     * @param securityContextHolder 安全上下文持有者
     * @return 认证过滤器
     */
    @Bean
    public AuthenticationFilter authenticationFilter(AuthenticationFactory authenticationFactory, SecurityContextHolder securityContextHolder) {
        return new AuthenticationFilter(authenticationFactory, securityContextHolder);
    }

    /**
     * 请求头过滤器。
     *
     * @param securityContextHolder 安全上下文持有者
     * @return 请求头过滤器
     */
    @Bean
    public RealIpUpdateFilter forwardedHeaderFilter(SecurityContextHolder securityContextHolder) {
        return new RealIpUpdateFilter(securityContextHolder);
    }

    /**
     * 登出过滤器。
     *
     * @param securityContextHolder 安全上下文持有者
     * @param logoutSuccessHandler  登出成功处理器
     * @return 登出过滤器
     */
    @Bean
    public LogoutFilter logoutFilter(SecurityContextHolder securityContextHolder, LogoutSuccessHandler logoutSuccessHandler) {
        return new LogoutFilter(securityContextHolder, logoutSuccessHandler);
    }

    /**
     * 角色鉴权过滤器。
     *
     * @param securityContextHolder 安全上下文持有者
     * @return 角色授权过滤器
     */
    @Bean
    public RoleAuthorizationFilter roleAuthorizationFilter(SecurityContextHolder securityContextHolder) {
        return new RoleAuthorizationFilter(securityContextHolder);
    }

    /**
     * 资源鉴权过滤器。
     *
     * @param securityContextHolder 安全上下文持有者
     * @return 资源鉴权过滤器
     */
    @Bean
    public ResourceAuthorizationFilter resourceAuthorizationFilter(SecurityContextHolder securityContextHolder) {
        return new ResourceAuthorizationFilter(securityContextHolder);
    }

    /**
     * 过滤器链执行器。
     *
     * @return 过滤器链执行器
     */
    @Bean
    public SecurityFilterChainExecutor securityFilterChainExecutor() {
        return new SecurityFilterChainExecutor();
    }
}
