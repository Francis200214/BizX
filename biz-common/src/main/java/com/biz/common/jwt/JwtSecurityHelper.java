package com.biz.common.jwt;

import com.biz.common.utils.Common;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;

/**
 * Jwt 与 Spring Security 之间融合的工具类
 *
 * @author francis
 * @create: 2024-01-04 09:27
 **/
public final class JwtSecurityHelper {

    private static final String AUTHORIZATION_STR = "Authorization";
    private static final String BEARER_STR = "Bearer ";
    private final JwtDecryptHelper JWT_TOKEN_DECRYPT_HELPER;

    private final String TOKEN;


    public JwtSecurityHelper(HttpServletRequest request, String secret, SignatureAlgorithm signatureAlgorithm) {
        this.TOKEN = subJwtTokenFromRequest(request);
        this.JWT_TOKEN_DECRYPT_HELPER = JwtDecryptHelper.decryptBuilder()
                .token(this.TOKEN)
                .secret(secret)
                .signatureAlgorithm(signatureAlgorithm)
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
     * 获取 Subject
     *
     * @return token
     */
    public String getSub() {
        return this.JWT_TOKEN_DECRYPT_HELPER.subject();
    }


    /**
     * 获取 Token 中某个 Key 的对应值
     *
     * @param key Key
     * @return 值
     */
    public String getDataByKey(String key) {
        return this.JWT_TOKEN_DECRYPT_HELPER.getByKey(key);
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


    /**
     * Jwt Security 构建者
     */
    public static class JwtSecurityBuilder {

        /**
         * HttpServletRequest
         */
        private HttpServletRequest request;

        /**
         * 密钥
         */
        private String secret;

        /**
         * 需要解密的 密钥算法 信息
         */
        private SignatureAlgorithm signatureAlgorithm;


        public JwtSecurityBuilder httpServletRequest(HttpServletRequest request) {
            this.request = request;
            return this;
        }

        /**
         * 解密密钥
         *
         * @param secret 密钥
         * @return JwtDecryptBuilder
         */
        public JwtSecurityBuilder secret(String secret) {
            this.secret = secret;
            return this;
        }

        /**
         * 密钥算法
         *
         * @param signatureAlgorithm 密钥算法
         * @return JwtDecryptBuilder
         */
        public JwtSecurityBuilder signatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
            this.signatureAlgorithm = signatureAlgorithm;
            return this;
        }

        public JwtSecurityHelper build() {
            return new JwtSecurityHelper(request, this.secret, this.signatureAlgorithm);
        }
    }

}
