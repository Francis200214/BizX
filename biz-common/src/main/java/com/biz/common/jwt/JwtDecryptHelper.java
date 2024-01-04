package com.biz.common.jwt;

import com.biz.common.utils.Common;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureAlgorithm;

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
     * 密钥算法
     */
    private SignatureAlgorithm SIGNATURE_ALGORITHM;

    /**
     * Token
     */
    private final String TOKEN;


    /**
     * 是否可用
     */
    private final boolean isExpire;

    /**
     * Token 中的 Subject 信息
     */
    private final Object SUBJECT;

    /**
     * Jws<Claims>
     */
    private final Jws<Claims> JWS_CLAIMS;


    public JwtDecryptHelper(String token, String secret, SignatureAlgorithm signatureAlgorithm) {
        this.TOKEN = token;
        this.SECRET = Common.isBlank(secret) ? JwtUtils.DEFAULT_SECRET : secret;
        this.SIGNATURE_ALGORITHM = signatureAlgorithm == null ? JwtUtils.DEFAULT_SIGNATURE_ALGORITHM : signatureAlgorithm;

        this.SUBJECT = JwtUtils.getSub(token, this.SECRET, this.SIGNATURE_ALGORITHM);
        this.isExpire = JwtUtils.checkToken(this.TOKEN, this.SECRET, this.SIGNATURE_ALGORITHM);
        this.JWS_CLAIMS = JwtUtils.getClaimsJws(this.TOKEN, this.SECRET, this.SIGNATURE_ALGORITHM);
    }


    /**
     * Token 是否有效
     *
     * @return 是否有效
     */
    public boolean isExpire() {
        return isExpire;
    }

    /**
     * 获取 Subject
     *
     * @param <T>
     * @return Subject 数据
     */
    public <T> T subject() {
        return (T) SUBJECT;
    }

    /**
     * 获取 Token 中某个 Key 对应的数据
     *
     * @param key key
     * @return key 对应的数据
     */
    public <T> T getByKey(String key) {
        return (T) JWS_CLAIMS.getBody().get(key);
    }


    /**
     * 创建 JwtToken 解密构建者
     *
     * @return JwtToken 解密构建者
     */
    public static JwtDecryptBuilder decryptBuilder() {
        return new JwtDecryptBuilder();
    }


    /**
     * JwtToken 解密构建者
     */
    public static class JwtDecryptBuilder {

        /**
         * 密钥
         */
        private String secret;

        /**
         * 需要解密的 Token 信息
         */
        private String token;

        /**
         * 需要解密的 密钥算法 信息
         */
        private SignatureAlgorithm signatureAlgorithm;

        /**
         * 设置需要解密的 Token 信息
         *
         * @param token token
         * @return JwtDecryptBuilder
         */
        public JwtDecryptBuilder token(String token) {
            this.token = token;
            return this;
        }

        /**
         * 解密密钥
         *
         * @param secret 密钥
         * @return JwtDecryptBuilder
         */
        public JwtDecryptBuilder secret(String secret) {
            this.secret = secret;
            return this;
        }

        /**
         * 密钥算法
         *
         * @param signatureAlgorithm 密钥算法
         * @return JwtDecryptBuilder
         */
        public JwtDecryptBuilder signatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
            this.signatureAlgorithm = signatureAlgorithm;
            return this;
        }

        public JwtDecryptHelper build() {
            return new JwtDecryptHelper(this.token, this.secret, this.signatureAlgorithm);
        }
    }


}
