package com.biz.common.jwt;

import com.biz.common.utils.Common;

/**
 * Jwt Token 解密
 *
 * @author francis
 * @create: 2024-01-04 10:03
 **/
public class JwtDecryptHelper {

    /**
     * 密钥
     */
    private final String SECRET;

    /**
     * Token
     */
    private final String TOKEN;

    private final boolean isExpire;


    public JwtDecryptHelper(String token, String secret) {
        this.TOKEN = token;
        this.SECRET = Common.isBlank(secret) ? JwtUtils.DEFAULT_SECRET : secret;
        this.isExpire = JwtUtils.checkToken(this.TOKEN);

    }


    /**
     * Token 是否有效
     *
     * @return
     */
    public boolean isExpire() {
        return isExpire;
    }


    /**
     * 创建 JwtToken 解密构建者
     *
     * @return JwtToken 解密构建者
     */
    public static JwtTokenDecryptBuilder decryptBuilder() {
        return new JwtTokenDecryptBuilder();
    }


    /**
     * JwtToken 解密构建者
     */
    public static class JwtTokenDecryptBuilder {

        /**
         * 密钥
         */
        private String secret;

        /**
         * 需要解密的 Token 信息
         */
        private String token;

        /**
         * 设置需要解密的 Token 信息
         *
         * @param token token
         * @return JwtTokenDecryptBuilder
         */
        public JwtTokenDecryptBuilder token(String token) {
            this.token = token;
            return this;
        }

        /**
         * 设置解密密钥
         *
         * @param secret 密钥
         * @return JwtTokenDecryptBuilder
         */
        public JwtTokenDecryptBuilder secret(String secret) {
            this.secret = secret;
            return this;
        }


        public JwtDecryptHelper build() {
            return new JwtDecryptHelper(this.token, this.secret);
        }

    }


}
