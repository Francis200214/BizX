package com.biz.security.authentication;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.security.authentication.validator.TokenValidator;
import com.biz.security.error.AuthenticationException;
import com.biz.security.error.SecurityErrorConstant;
import com.biz.security.user.UserDetails;
import com.biz.security.user.UserDetailsStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * TokenAuthenticationService 实现 Token 认证方式。
 *
 * <p>
 * 使用 Token 来验证用户身份，常用于无状态认证。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
@Slf4j
public class TokenAuthenticationService implements AuthenticationService, SmartInitializingSingleton {

    /**
     * 获取用户信息策略。
     */
    private UserDetailsStrategy userDetailsStrategy;

    /**
     * Token 验证器。
     */
    private TokenValidator tokenValidator;

    /**
     * Token 认证，使用 Token 验证用户身份。
     *
     * @param loginRequest 包含 Token 的登录请求
     * @return {@link UserDetails} 返回经过认证的用户信息
     * @throws AuthenticationException 当认证失败时抛出异常
     */
    @Override
    public UserDetails authenticate(LoginRequest loginRequest) throws AuthenticationException {
        String token = loginRequest.getToken();

        if (token == null) {
            throw new AuthenticationException(SecurityErrorConstant.TOKEN_INFORMATION_IS_MISSING_FAILED);
        }

        // Token 验证
        if (isValidToken(token)) {
            return this.findUserDetailsByToken(token);
        } else {
            throw new AuthenticationException(SecurityErrorConstant.TOKEN_AUTHENTICATION_FAILED);
        }
    }

    /**
     * 验证 Token 是否有效。
     *
     * @param token 用户 Token
     * @return Token 是否有效
     */
    private boolean isValidToken(String token) {
        if (tokenValidator == null) {
            throw new RuntimeException("TokenValidator 校验 Token 是否有效未实现！");
        }
        return tokenValidator.validate(token);
    }

    /**
     * 根据 Token 获取用户信息。
     *
     * @param token 用户 Token
     * @return {@link UserDetails} 用户信息
     * @throws RuntimeException 当获取用户信息失败时抛出异常
     */
    private UserDetails findUserDetailsByToken(String token) throws RuntimeException {
        UserDetails userDetails = userDetailsStrategy.loadUserByToken(token);
        if (userDetails == null) {
            throw new RuntimeException("UserDetails is null");
        }
        return userDetails;
    }

    /**
     * 在所有单例初始化后，设置必要的依赖。
     */
    @Override
    public void afterSingletonsInstantiated() {
        try {
            this.tokenValidator = BizXBeanUtils.getBean(TokenValidator.class);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("没有对 TokenValidator Token校验实现 Bean");
            }
        }
        this.userDetailsStrategy = BizXBeanUtils.getBean(UserDetailsStrategy.class);
    }
}
