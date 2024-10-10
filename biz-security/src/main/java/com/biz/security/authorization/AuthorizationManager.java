package com.biz.security.authorization;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;
import com.biz.security.user.UserDetails;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.List;
import java.util.Map;

/**
 * 权限管理类，负责用户的权限校验逻辑
 *
 * <p>
 *     负责用户权限校验的整体调度，将权限的具体判断逻辑交给多个AuthorizationHandler。
 * </p>
 *
 * @author francis
 * @create 2024-09-13
 * @since 1.0.1
 **/
public class AuthorizationManager implements AuthorizationService, SmartInitializingSingleton {

    /**
     * 权限处理器列表
     */
    private static List<AuthorizationHandler> handlers;

    /**
     * 校验用户是否有权限访问资源
     *
     * @param userDetails 用户信息
     * @param resource    资源名称
     * @return true：有权限，false：无权限
     */
    public boolean authorize(UserDetails userDetails, String resource) {
        if (Common.isEmpty(handlers)) {
            return false;
        }
        for (AuthorizationHandler handler : handlers) {
            if (!handler.check(userDetails, resource)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, AuthorizationHandler> beansOfType = BizXBeanUtils.getBeansOfType(AuthorizationHandler.class);
        handlers = (List<AuthorizationHandler>) beansOfType.values();
    }

}
