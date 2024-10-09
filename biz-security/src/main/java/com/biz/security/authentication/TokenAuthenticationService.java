package com.biz.security.authentication;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.security.authentication.validator.TokenValidator;
import com.biz.security.error.AuthenticationException;
import com.biz.security.error.SecurityErrorConstant;
import com.biz.security.user.UserDetails;
import com.biz.security.user.UserDetailsStrategy;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * TokenAuthenticationService 实现Token认证方式。
 *
 * <p>
 * 使用Token来验证用户身份，常用于无状态认证。
 * </p>
 *
 * @author francis
 * @create 2024-09-20
 * @since 1.0.1
 **/
public class TokenAuthenticationService implements AuthenticationService, SmartInitializingSingleton {

    /**
     * 获取用户信息策略
     */
    private UserDetailsStrategy userDetailsStrategy;

    /**
     * Token验证器
     */
    private TokenValidator tokenValidator;

    /**
     * Token认证，使用Token验证用户身份
     *
     * @param loginRequest 包含Token的登录请求
     * @return UserDetails 返回经过认证的用户信息
     * @throws AuthenticationException 当认证失败时抛出异常
     */
    @Override
    public UserDetails authenticate(LoginRequest loginRequest) throws AuthenticationException {
        String token = loginRequest.getToken();

        if (token == null) {
            throw new AuthenticationException(SecurityErrorConstant.TOKEN_INFORMATION_IS_MISSING_FAILED);
        }

        // Token验证
        if (isValidToken(token)) {
            return this.findUserDetailsByToken(token);

        } else {
            throw new AuthenticationException(SecurityErrorConstant.TOKEN_AUTHENTICATION_FAILED);

        }
    }

    /**
     * 模拟方法，验证Token是否有效
     *
     * @param token 用户Token
     * @return Token是否有效
     */
    private boolean isValidToken(String token) {
        return tokenValidator.validate(token);
    }

    /**
     * 根据Token获取用户信息
     *
     * @param token 用户Token
     * @return UserDetails 用户信息
     * @throws RuntimeException 当获取用户信息失败时抛出异常
     */
    private UserDetails findUserDetailsByToken(String token) throws RuntimeException {
        UserDetails userDetails = userDetailsStrategy.loadUserByToken(token);
        if (userDetails == null) {
            throw new RuntimeException("UserDetails is null");
        }

        return userDetails;
    }


    @Override
    public void afterSingletonsInstantiated() {
        this.tokenValidator = BizXBeanUtils.getBean(TokenValidator.class);
        this.userDetailsStrategy = BizXBeanUtils.getBean(UserDetailsStrategy.class);
    }

}
