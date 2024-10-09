package com.biz.security.authentication;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.security.authentication.validator.OAuth2TokenValidator;
import com.biz.security.error.AuthenticationException;
import com.biz.security.error.SecurityErrorConstant;
import com.biz.security.user.UserDetails;
import com.biz.security.user.UserDetailsStrategy;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.Collections;

/**
 * OAuth2AuthenticationService 实现OAuth2认证方式的逻辑。
 *
 * <p>
 *     通过OAuth2的Token与第三方认证服务提供商（如Google、Facebook等）进行交互验证。
 * </p>
 *
 * @author francis
 * @create 2024-09-20
 * @since 1.0.1
 **/
public class OAuth2AuthenticationService implements AuthenticationService, SmartInitializingSingleton {


    /**
     * 获取用户信息策略
     */
    private UserDetailsStrategy userDetailsStrategy;

    /**
     * OAuth2 Token验证器
     */
    private OAuth2TokenValidator oAuth2TokenValidator;

    /**
     * OAuth2认证，使用提供商和Token进行认证。
     *
     * @param loginRequest 包含OAuth2 Token和提供商信息的登录请求
     * @return UserDetails 返回经过认证的用户信息
     * @throws AuthenticationException 当认证失败时抛出异常
     */
    @Override
    public UserDetails authenticate(@NotNull LoginRequest loginRequest) throws AuthenticationException {
        String token = loginRequest.getToken();
        String provider = loginRequest.getOAuth2Provider();

        if (token == null || provider == null) {
            throw new AuthenticationException(SecurityErrorConstant.O_AUTH_2_INFORMATION_IS_MISSING_FAILED);
        }

        // 与OAuth2服务提供商进行交互验证
        if (isValidOAuth2Token(provider, token)) {
            return this.findUserDetailsByOAuth2(token);
        } else {
            throw new AuthenticationException(SecurityErrorConstant.O_AUTH_2_AUTHENTICATION_FAILED);
        }
    }

    /**
     * 模拟方法，验证OAuth2 Token是否有效
     *
     * @param provider OAuth2提供商
     * @param token OAuth2 Token
     * @return 是否通过验证
     */
    private boolean isValidOAuth2Token(String provider, String token) {
        return oAuth2TokenValidator.validate(provider, token);
    }

    /**
     * 根据OAuth2信息查找用户信息
     *
     * @param oAuth2 OAuth2信息
     * @return UserDetails 用户信息
     * @throws RuntimeException 当用户信息为空时抛出异常
     */
    private UserDetails findUserDetailsByOAuth2(String oAuth2) throws RuntimeException {
        UserDetails userDetails = userDetailsStrategy.loadUserByOAuth2(oAuth2);
        if (userDetails == null) {
            throw new RuntimeException("UserDetails is null");
        }
        return userDetails;
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.userDetailsStrategy = BizXBeanUtils.getBean(UserDetailsStrategy.class);
        this.oAuth2TokenValidator = BizXBeanUtils.getBean(OAuth2TokenValidator.class);
    }
}
