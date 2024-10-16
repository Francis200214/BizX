package com.biz.security.config;

import com.biz.security.authorization.AuthorizationManager;
import com.biz.security.authorization.AuthorizationService;
import com.biz.security.authorization.handler.ResourceAuthorizationHandler;
import com.biz.security.authorization.handler.RoleAuthorizationHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 鉴权配置。
 *
 * <p>
 * 配置用于 Biz-Security 组件的鉴权服务。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
@ConditionalOnProperty(prefix = "biz.security", name = "enabled", havingValue = "true")
public class AuthorizationConfigurer {

    /**
     * 鉴权服务实例。
     *
     * @return AuthorizationManager 实例
     */
    @Bean
    public AuthorizationService authorizationService() {
        return new AuthorizationManager();
    }

    /**
     * 资源鉴权处理器实例。
     *
     * @return ResourceAuthorizationHandler 实例
     */
    @Bean
    public ResourceAuthorizationHandler resourceAuthorizationHandler() {
        return new ResourceAuthorizationHandler();
    }

    /**
     * 角色鉴权处理器实例。
     *
     * @return RoleAuthorizationHandler 实例
     */
    @Bean
    public RoleAuthorizationHandler roleAuthorizationHandler() {
        return new RoleAuthorizationHandler();
    }
}
