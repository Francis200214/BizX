package com.biz.security.authentication;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.security.authentication.validator.OAuth2TokenValidator;
import com.biz.security.error.AuthenticationException;
import com.biz.security.error.SecurityErrorConstant;
import com.biz.security.user.UserDetails;
import com.biz.security.user.UserDetailsStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * OAuth2AuthenticationService 实现 OAuth2 认证方式的逻辑。
 *
 * <p>
 * 通过 OAuth2 的 Token 与第三方认证服务提供商（如 Google、Facebook 等）进行交互验证。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
@Slf4j
public class OAuth2AuthenticationService implements AuthenticationService, SmartInitializingSingleton {

    /**
     * 获取用户信息策略。
     */
    private UserDetailsStrategy userDetailsStrategy;

    /**
     * OAuth2 Token 验证器。
     */
    private OAuth2TokenValidator oAuth2TokenValidator;

    /**
     * OAuth2 认证，使用提供商和 Token 进行认证。
     *
     * @param loginRequest 包含 OAuth2 Token 和提供商信息的登录请求
     * @return {@link UserDetails} 返回经过认证的用户信息
     * @throws AuthenticationException 当认证失败时抛出异常
     */
    @Override
    public UserDetails authenticate(LoginRequest loginRequest) throws AuthenticationException {
        String token = loginRequest.getToken();
        String provider = loginRequest.getOAuth2Provider();

        if (token == null || provider == null) {
            throw new AuthenticationException(SecurityErrorConstant.O_AUTH_2_INFORMATION_IS_MISSING_FAILED);
        }

        // 与 OAuth2 服务提供商进行交互验证
        if (isValidOAuth2Token(provider, token)) {
            return this.findUserDetailsByOAuth2(provider, token);
        } else {
            throw new AuthenticationException(SecurityErrorConstant.O_AUTH_2_AUTHENTICATION_FAILED);
        }
    }

    /**
     * 模拟方法，验证 OAuth2 Token 是否有效。
     *
     * @param provider OAuth2 提供商
     * @param token    OAuth2 Token
     * @return 是否通过验证
     */
    private boolean isValidOAuth2Token(String provider, String token) {
        if (oAuth2TokenValidator == null) {
            throw new RuntimeException("OAuth2TokenValidator 校验 OAuth2 Token 是否有效未实现！");
        }
        return oAuth2TokenValidator.validate(provider, token);
    }

    /**
     * 根据 OAuth2 信息查找用户信息。
     *
     * @param provider OAuth2 提供商
     * @param oAuth2   OAuth2 信息
     * @return {@link UserDetails} 用户信息
     * @throws RuntimeException 当用户信息为空时抛出异常
     */
    private UserDetails findUserDetailsByOAuth2(String provider, String oAuth2) throws RuntimeException {
        UserDetails userDetails = userDetailsStrategy.loadUserByOAuth2(provider, oAuth2);
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
        this.userDetailsStrategy = BizXBeanUtils.getBean(UserDetailsStrategy.class);
        try {
            this.oAuth2TokenValidator = BizXBeanUtils.getBean(OAuth2TokenValidator.class);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("没有对 OAuth2TokenValidator Token校验实现 Bean");
            }
        }
    }
}