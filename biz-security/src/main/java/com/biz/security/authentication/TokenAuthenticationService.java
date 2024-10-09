package com.biz.security.authentication;

import com.biz.security.error.AuthenticationException;
import com.biz.security.error.SecurityErrorConstant;
import com.biz.security.user.UserDetails;

import java.util.Collections;

/**
 * TokenAuthenticationService 实现Token认证方式。
 *
 * <p>
 *     使用Token来验证用户身份，常用于无状态认证。
 * </p>
 *
 * @author francis
 * @create 2024-09-20
 * @since 1.0.1
 **/
public class TokenAuthenticationService implements AuthenticationService {

    /**
     * Token认证，使用Token验证用户身份
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

        // 模拟Token验证
        if (isValidToken(token)) {
            // 假设Token验证成功，返回用户信息
            return new UserDetails("token_user", "78910", Collections.singletonList("ADMIN"));
        } else {
            throw new AuthenticationException(SecurityErrorConstant.TOKEN_AUTHENTICATION_FAILED);
        }
    }

    /**
     * 模拟方法，验证Token是否有效
     * @param token 用户Token
     * @return Token是否有效
     */
    private boolean isValidToken(String token) {
        // 实际项目中可能是解析JWT或通过第三方服务验证Token的合法性
        return "valid_token".equals(token);
    }

}
