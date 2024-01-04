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
public final class JwtCreateHelper {


    /**
     * JwtCreateHelper 加密密钥
     */
    private final String SECRET;

    /**
     * JwtCreateHelper 有效期（15天）
     */
    private final long EXPIRE;

    /**
     * JwtCreateHelper 加密算法
     */
    private final SignatureAlgorithm SIGNATURE_ALGORITHM;

    /**
     * Token 参数
     */
    private final Map<String, Object> DATA;


    public JwtCreateHelper(String secret, long expire, SignatureAlgorithm signatureAlgorithm, Map<String, Object> data) {
        this.SECRET = Optional.ofNullable(secret).orElse(JwtUtils.DEFAULT_SECRET);
        this.EXPIRE = expire <= 0 ? JwtUtils.DEFAULT_EXPIRE : expire;
        this.SIGNATURE_ALGORITHM = Optional.ofNullable(signatureAlgorithm).orElse(JwtUtils.DEFAULT_SIGNATURE_ALGORITHM);
        this.DATA = data;
    }


    /**
     * 创建 Token
     *
     * @return token值
     */
    public String createToken() {
        return JwtUtils.createToken(SECRET, EXPIRE, SIGNATURE_ALGORITHM, DATA);
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
        return JwtUtils.getData(token, key, secret);
    }

    /**
     * 获取 Token 中某个 Key 的值
     *
     * @param token Token
     * @param key   key
     * @return
     */
    public Object getData(String token, String key) {
        return JwtUtils.getData(token, key, SECRET);
    }

    /**
     * 判断 token 是否可用
     *
     * @param token
     * @return
     */
    public boolean checkExpire(String token) {
        return JwtUtils.checkToken(token, SECRET);
    }


    public static JwtTokenCreateBuilder builder() {
        return new JwtTokenCreateBuilder();
    }


    /**
     * 创建 JwtToken 构建者
     */
    public static class JwtTokenCreateBuilder {
        /**
         * 密钥
         */
        private String secret;

        /**
         * JwtCreateHelper 有效期
         */
        private long expire;

        /**
         * 需要加密的 Token 信息
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

        public JwtCreateHelper build() {
            return new JwtCreateHelper(secret, expire, signatureAlgorithm, data);
        }

    }

}
