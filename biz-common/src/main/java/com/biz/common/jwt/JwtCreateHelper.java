package com.biz.common.jwt;

import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Jwt 创建者
 * <p>用于生成和管理 JWT Token。</p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 * JwtCreateHelper jwtHelper = new JwtCreateHelper("mySecret", 3600000, SignatureAlgorithm.HS256, null);
 * String token = jwtHelper.createToken();
 *
 * // 使用构建器模式创建JwtCreateHelper
 * JwtCreateHelper customHelper = JwtCreateHelper.builder()
 *     .secret("mySecret")
 *     .expire(3600000)
 *     .signatureAlgorithm(SignatureAlgorithm.HS256)
 *     .build();
 * }
 * </pre>
 *
 * <p>该类提供了创建JWT Token、管理载荷数据和验证Token有效性的方法。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 **/
public final class JwtCreateHelper {
    /**
     * 用于JWT签名的密钥。
     */
    private final String secret;
    /**
     * JWT的过期时间，单位为毫秒。
     */
    private final long expire;
    /**
     * JWT的签名算法。
     */
    private final SignatureAlgorithm signatureAlgorithm;
    /**
     * JWT载荷中的数据。
     */
    private final Map<String, Object> data;

    /**
     * 构造函数，初始化JWT创建辅助类。
     *
     * @param secret             签名密钥，为空时使用默认密钥。
     * @param expire             Token的过期时间，小于等于0时使用默认过期时间。
     * @param signatureAlgorithm 签名算法，为空时使用默认算法。
     * @param data               Token的数据载荷，可能为空。
     */
    public JwtCreateHelper(String secret, long expire, SignatureAlgorithm signatureAlgorithm, Map<String, Object> data) {
        this.secret = Optional.ofNullable(secret).orElse(JwtUtils.DEFAULT_SECRET);
        this.expire = expire <= 0 ? (System.currentTimeMillis() + JwtUtils.DEFAULT_EXPIRE) : expire;
        this.signatureAlgorithm = Optional.ofNullable(signatureAlgorithm).orElse(JwtUtils.DEFAULT_SIGNATURE_ALGORITHM);
        this.data = data;
    }

    /**
     * 创建一个JWT Token。
     *
     * @return 创建的JWT Token字符串。
     */
    public String createToken() {
        return JwtUtils.createToken(this.secret, this.expire, this.signatureAlgorithm, this.data);
    }

    /**
     * 向数据载荷中添加数据。
     *
     * @param key   要添加的数据的键。
     * @param value 要添加的数据的值。
     */
    public void putData(String key, Object value) {
        this.data.put(key, value);
    }

    /**
     * 从JWT Token中获取指定键的数据。
     *
     * @param token  JWT Token字符串。
     * @param key    指定的键。
     * @param secret 解密Token的密钥。
     * @return 键对应的数据。
     */
    public Object getData(String token, String key, String secret) {
        return JwtUtils.getData(token, key, secret);
    }

    /**
     * 从JWT Token中获取指定键的数据，使用构造函数中提供的密钥。
     *
     * @param token JWT Token字符串。
     * @param key   指定的键。
     * @return 键对应的数据。
     */
    public Object getData(String token, String key) {
        return JwtUtils.getData(token, key, this.secret);
    }

    /**
     * 检查JWT Token是否过期。
     *
     * @param token JWT Token字符串。
     * @return 如果Token未过期返回true，否则返回false。
     */
    public boolean checkExpire(String token) {
        return JwtUtils.checkToken(token, this.secret);
    }

    /**
     * 获取一个JWT Token构建器，用于逐步构建JWT Token。
     *
     * @return JWT Token构建器实例。
     */
    public static JwtTokenCreateBuilder builder() {
        return new JwtTokenCreateBuilder();
    }

    /**
     * JWT Token构建器，提供链式调用以构建JwtCreateHelper实例。
     */
    public static class JwtTokenCreateBuilder {
        /**
         * 用于JWT签名的密钥。
         */
        private String secret;
        /**
         * JWT的过期时间，单位为毫秒。
         */
        private long expire;
        /**
         * JWT的签名算法。
         */
        private SignatureAlgorithm signatureAlgorithm;
        /**
         * JWT载荷中的数据。
         */
        private Map<String, Object> data = new HashMap<>();

        private JwtTokenCreateBuilder() {
        }

        /**
         * 设置JWT签名的密钥。
         *
         * @param secret 密钥。
         * @return 当前构建器实例。
         */
        public JwtTokenCreateBuilder secret(String secret) {
            this.secret = secret;
            return this;
        }

        /**
         * 设置JWT的过期时间。
         *
         * @param expire 过期时间，单位为毫秒。
         * @return 当前构建器实例。
         */
        public JwtTokenCreateBuilder expire(long expire) {
            this.expire = expire;
            return this;
        }

        /**
         * 设置JWT的签名算法。
         *
         * @param signatureAlgorithm 签名算法。
         * @return 当前构建器实例。
         */
        public JwtTokenCreateBuilder signatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
            this.signatureAlgorithm = signatureAlgorithm;
            return this;
        }

        /**
         * 向JWT载荷中添加数据。
         *
         * @param key   数据的键。
         * @param value 数据的值。
         * @return 当前构建器实例。
         */
        public JwtTokenCreateBuilder data(String key, Object value) {
            this.data.put(key, value);
            return this;
        }

        /**
         * 构建一个JwtCreateHelper实例。
         *
         * @return 构建的JwtCreateHelper实例。
         */
        public JwtCreateHelper build() {
            return new JwtCreateHelper(secret, expire, signatureAlgorithm, data);
        }
    }
}
