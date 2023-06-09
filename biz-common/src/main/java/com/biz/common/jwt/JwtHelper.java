package com.biz.common.jwt;

import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Map;
import java.util.Optional;

/**
 * Jwt 创建者
 *
 * @author francis
 * @create: 2023-04-22 14:02
 **/
public final class JwtHelper {


    /**
     * JwtHelper 加密密钥
     */
    private final String SECRET;

    /**
     * JwtHelper 有效期（15天）
     */
    private final long EXPIRE;

    /**
     * JwtHelper 加密算法
     */
    private final SignatureAlgorithm SIGNATURE_ALGORITHM;

    /**
     * Token 值
     */
    private final Map<String, Object> DATA;

    public JwtHelper(String secret, long expire, SignatureAlgorithm signatureAlgorithm, Map<String, Object> data) {
        SECRET = Optional.ofNullable(secret).orElse(JwtTokenUtils.DEFAULT_SECRET);
        EXPIRE = expire <= 0 ? JwtTokenUtils.DEFAULT_EXPIRE : expire;
        SIGNATURE_ALGORITHM = Optional.ofNullable(signatureAlgorithm).orElse(JwtTokenUtils.DEFAULT_SIGNATURE_ALGORITHM);
        DATA = data;
    }

    /**
     * 创建 Token
     *
     * @return token值
     */
    public String createToken() {
        return JwtTokenUtils.createToken(SECRET, EXPIRE, SIGNATURE_ALGORITHM, DATA);
    }

    /**
     * 保存 Token Data
     *
     * @param key   key
     * @param value 值
     */
    public void putData(String key, Object value) {
        DATA.put(key, value);
    }

    /**
     * 获取 Token 中某个 Key 的值
     *
     * @param token  Token
     * @param key    key
     * @param secret 密钥
     * @return
     */
    public Object getData(String token, String key, String secret) {
        return JwtTokenUtils.getData(token, key, secret);
    }

    /**
     * 获取 Token 中某个 Key 的值
     *
     * @param token Token
     * @param key   key
     * @return
     */
    public Object getData(String token, String key) {
        return JwtTokenUtils.getData(token, key, SECRET);
    }

    /**
     * 判断 token 是否可用
     *
     * @param token
     * @return
     */
    public boolean checkExpire(String token) {
        return JwtTokenUtils.checkToken(token, SECRET);
    }


    public static JwtTokenCreateBuilder builder() {
        return new JwtTokenCreateBuilder();
    }


    public static class JwtTokenCreateBuilder {
        private String secret;

        /**
         * JwtHelper 有效期
         */
        private long expire;

        /**
         * Token 信息
         */
        private Map<String, Object> data;

        /**
         * 加密算法
         */
        private SignatureAlgorithm signatureAlgorithm;

        private JwtTokenCreateBuilder() {

        }

        public JwtTokenCreateBuilder secret(String secret) {
            this.secret = secret;
            return this;
        }

        public JwtTokenCreateBuilder expire(long expire) {
            this.expire = expire;
            return this;
        }

        public JwtTokenCreateBuilder signatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
            this.signatureAlgorithm = signatureAlgorithm;
            return this;
        }

        public JwtTokenCreateBuilder data(String key, Object value) {
            this.data.put(key, value);
            return this;
        }

        public JwtHelper build() {
            return new JwtHelper(secret, expire, signatureAlgorithm, data);
        }

    }

}
