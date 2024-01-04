package com.biz.common.jwt;

import com.biz.common.utils.Common;

import javax.servlet.http.HttpServletRequest;

/**
 * Jwt 与 Spring Security 之间融合的工具类
 *
 * @author francis
 * @create: 2024-01-04 09:27
 **/
public class JwtSecurityHelper {

    private static final String AUTHORIZATION_STR = "Authorization";
    private static final String BEARER_STR = "Bearer ";
    private final JwtDecryptHelper JWT_TOKEN_DECRYPT_HELPER;

    private final String TOKEN;


    public JwtSecurityHelper(HttpServletRequest request) {
        this.TOKEN = subJwtTokenFromRequest(request);
        this.JWT_TOKEN_DECRYPT_HELPER = JwtDecryptHelper.decryptBuilder()
                .token(this.TOKEN)
                .build();
    }


    /**
     * 检查 Token 是否过期
     *
     * @return Token 是否过期
     */
    public boolean isExpire() {
        return JWT_TOKEN_DECRYPT_HELPER.isExpire();
    }


    /**
     * 检查是否有 Token
     *
     * @return 是否有 Token
     */
    public boolean isToken() {
        return Common.isNotBlank(TOKEN);
    }


    /**
     * 从 HttpServletRequest 中取出 Token
     *
     * @param request HttpServletRequest 对象
     * @return Token, 如果是 Null 则代表没有 Token
     */
    private String subJwtTokenFromRequest(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String bearerToken = request.getHeader(AUTHORIZATION_STR);
        if (Common.isNotBlank(bearerToken) && bearerToken.startsWith(BEARER_STR)) {
            return bearerToken.substring(7);
        }

        return null;
    }

}
