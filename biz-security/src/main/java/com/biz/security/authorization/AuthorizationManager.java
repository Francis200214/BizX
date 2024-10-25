package com.biz.security.authorization;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.security.authorization.handler.ResourceAuthorizationHandler;
import com.biz.security.authorization.handler.RoleAuthorizationHandler;
import com.biz.security.user.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * 权限管理类，负责用户的权限校验逻辑。
 *
 * <p>
 * 负责用户权限校验的整体调度，将权限的具体判断逻辑交给多个 {@link AuthorizationHandler}。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-13
 */
@Slf4j
public class AuthorizationManager implements AuthorizationService, SmartInitializingSingleton {

    /**
     * 资源权限处理器。
     */
    private ResourceAuthorizationHandler resourceAuthorizationHandler;

    /**
     * 角色权限处理器。
     */
    private RoleAuthorizationHandler roleAuthorizationHandler;

    /**
     * 校验用户是否有权限访问资源。
     *
     * @param userDetails 用户信息
     * @param resource    资源名称
     * @return {@code true} 如果用户有权限访问资源，否则返回 {@code false}
     */
    @Override
    public boolean authorizeResource(UserDetails userDetails, String resource) {
        if (resourceAuthorizationHandler == null) {
            if (log.isDebugEnabled()) {
                log.debug("没有权限处理器");
            }
            return false;
        }
        return resourceAuthorizationHandler.check(userDetails, resource);
    }

    /**
     * 校验用户是否有权限访问角色。
     *
     * @param userDetails 用户信息
     * @param role        角色名称
     * @return {@code true} 如果用户有权限访问角色，否则返回 {@code false}
     */
    @Override
    public boolean authorizeRole(UserDetails userDetails, String role) {
        if (roleAuthorizationHandler == null) {
            if (log.isDebugEnabled()) {
                log.debug("没有角色处理器");
            }
            return false;
        }
        return roleAuthorizationHandler.check(userDetails, role);
    }

    /**
     * 在所有单例初始化后，初始化权限处理器列表。
     */
    @Override
    public void afterSingletonsInstantiated() {
        try {
            resourceAuthorizationHandler = BizXBeanUtils.getBean(ResourceAuthorizationHandler.class);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("没有资源权限处理器 ResourceAuthorizationHandler Bean");
            }
            throw new NoSuchBeanDefinitionException("没有资源权限处理器 ResourceAuthorizationHandler Bean");
        }

        try {
            roleAuthorizationHandler = BizXBeanUtils.getBean(RoleAuthorizationHandler.class);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("没有角色权限处理器 RoleAuthorizationHandler Bean");
            }
            throw new NoSuchBeanDefinitionException("没有角色权限处理器 RoleAuthorizationHandler Bean");
        }

    }
}
