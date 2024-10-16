package com.biz.security.config;

import com.biz.security.user.DefaultUserDetailsStrategy;
import com.biz.security.user.UserDetailsStrategy;
import com.biz.security.user.store.SecurityContextHolder;
import com.biz.security.user.store.SecurityContextStrategy;
import com.biz.security.user.store.ThreadLocalSecurityContextStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 用户信息配置。
 *
 * <p>
 * 配置用于 Biz-Security 组件的用户信息存储和策略。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
@ConditionalOnProperty(prefix = "biz.security", name = "enabled", havingValue = "true")
public class UserDetailsStoreConfigurer {

    /**
     * 默认使用本地存储实现。
     *
     * @return SecurityContextStrategy 实例
     */
    @Bean
    @ConditionalOnMissingBean(SecurityContextStrategy.class)
    public SecurityContextStrategy securityContextStrategy() {
        return new ThreadLocalSecurityContextStrategy();
    }

    /**
     * 默认使用默认实现。
     *
     * @return UserDetailsStrategy 实例
     */
    @Bean
    @ConditionalOnMissingBean(UserDetailsStrategy.class)
    public UserDetailsStrategy userDetailsStrategy() {
        return new DefaultUserDetailsStrategy();
    }

    /**
     * 配置安全上下文持有者。
     *
     * @return SecurityContextHolder 实例
     */
    @Bean
    public SecurityContextHolder securityContextHolder() {
        return new SecurityContextHolder();
    }
}
