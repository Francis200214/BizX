package com.biz.security.authentication;

import com.biz.security.error.AuthenticationException;
import com.biz.security.error.SecurityErrorConstant;
import com.biz.security.user.UserDetails;
import com.sun.istack.internal.NotNull;

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
public class OAuth2AuthenticationService implements AuthenticationService {

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

        // 模拟与OAuth2服务提供商进行交互验证
        if (isValidOAuth2Token(provider, token)) {
            // 假设验证成功，返回用户信息
            return new UserDetails("oauth2_user", "123456", Collections.singletonList("USER"));
        } else {
            throw new AuthenticationException(SecurityErrorConstant.O_AUTH_2_AUTHENTICATION_FAILED);
        }
    }

    /**
     * 模拟方法，验证OAuth2 Token是否有效
     * @param provider OAuth2提供商
     * @param token OAuth2 Token
     * @return 是否通过验证
     */
    private boolean isValidOAuth2Token(String provider, String token) {
        // 这里应该是与第三方服务提供商的交互代码
        // 例如：调用Google或Facebook的API
        // 此处简化为模拟验证
        return "valid_token".equals(token) && "google".equals(provider);
    }


}
