package com.biz.web.interceptor;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.web.account.BizAccount;
import com.biz.web.error.ErrorCode;
import com.biz.web.error.IF;
import com.biz.web.rbac.BizVerification;
import com.biz.web.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 检查用户权限
 *
 * @author francis
 * @since 1.0.1
 **/
@Slf4j
public class CheckAuthorityInterceptor implements HandlerInterceptor, ApplicationListener<ContextRefreshedEvent>, Ordered {

    private static Token token;

    private static final String AUTH_ALL = "*";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到Controller方法直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        BizVerification verification = method.getAnnotation(BizVerification.class);
        if (verification == null) {
            return true;
        }

        BizAccount<?> currentUser = token.getCurrentUser();
        IF.isNull(currentUser, ErrorCode.ACCOUNT_NOT_INIT);

        // 检查用户是否有访问该接口的权限权限
        this.handleBizVerification(currentUser.getRoles(), verification.roles());
        return true;
    }


    /**
     * 检查当前用户的权限是否包含接口所需要的权限
     * 如果用户的权限是 null 则抛出无权限异常
     *
     * @param currentUserRoles 当前用户权限组
     * @param roles            接口需要的权限组
     */
    private void handleBizVerification(Set<String> currentUserRoles, String[] roles) {
        if (roles.length == 1) {
            String role = roles[0];
            if (!AUTH_ALL.equals(role)) {
                this.handleUserRole(currentUserRoles, roles);
            }
        } else {
            this.handleUserRole(currentUserRoles, roles);
        }
    }


    /**
     * 检查当前用户的权限是否包含接口所需要的权限
     * 如果用户的权限是 null 则抛出无权限异常
     *
     * @param currentUserRoles 当前用户权限组
     * @param roles            接口需要的权限组
     */
    private void handleUserRole(Set<String> currentUserRoles, String[] roles) {
        IF.isNull(currentUserRoles, ErrorCode.NO_AUTH);

        for (String role : roles) {
            IF.is(!currentUserRoles.contains(role), ErrorCode.NO_AUTH);
        }
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            token = BizXBeanUtils.getBean(Token.class);
        } catch (Exception e) {
            throw new RuntimeException("Token bean is not definition");
        }
    }

    @Override
    public int getOrder() {
        return 101;
    }

}
