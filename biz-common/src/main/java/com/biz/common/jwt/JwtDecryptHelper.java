package com.biz.common.jwt;

import com.biz.common.utils.Common;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Jwt Token 解密工具类
 * <p>用于解密和解析 JWT Token，提供了检查 Token 是否过期、获取 Token 中的 Subject 和指定键对应的值等功能。</p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 * JwtDecryptHelper decryptHelper = JwtDecryptHelper.decryptBuilder()
 *     .token("your-jwt-token")
 *     .secret("your-secret")
 *     .signatureAlgorithm(SignatureAlgorithm.HS256)
 *     .build();
 *
 * boolean isExpired = decryptHelper.isExpired();
 * String subject = decryptHelper.getSubject();
 * Object value = decryptHelper.getByKey("key");
 * }</pre>
 *
 * <p>该类依赖于 {@link JwtUtils} 和 {@link Common} 类提供的工具方法。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
public class JwtDecryptHelper {

    // 密钥
    private final String secret;

    // 密钥算法
    private final SignatureAlgorithm signatureAlgorithm;

    // Token
    private final String token;

    // 是否已过期
    private final boolean isExpired;

    // Token 中的 Subject 信息
    private final Object subject;

    // Jws<Claims> 对象
    private final Jws<Claims> jwsClaims;

    /**
     * 构造函数，初始化 JwtDecryptHelper 对象。
     *
     * @param token              JWT Token
     * @param secret             密钥
     * @param signatureAlgorithm 签名算法
     */
    public JwtDecryptHelper(String token, String secret, SignatureAlgorithm signatureAlgorithm) {
        this.token = token;
        this.secret = Common.isBlank(secret) ? JwtUtils.DEFAULT_SECRET : secret;
        this.signatureAlgorithm = signatureAlgorithm == null ? JwtUtils.DEFAULT_SIGNATURE_ALGORITHM : signatureAlgorithm;

        this.subject = JwtUtils.getSub(token, this.secret, this.signatureAlgorithm);
        this.isExpired = JwtUtils.checkToken(this.token, this.secret, this.signatureAlgorithm);
        this.jwsClaims = JwtUtils.getClaimsJws(this.token, this.secret, this.signatureAlgorithm);
    }

    /**
     * 检查 Token 是否过期。
     *
     * @return 是否过期
     */
    public boolean isExpired() {
        return isExpired;
    }

    /**
     * 获取 Token 中的 Subject 信息。
     *
     * @param <T> Subject 的类型
     * @return Subject 数据
     */
    public <T> T getSubject() {
        return Common.to(subject);
    }

    /**
     * 获取 Token 中指定 Key 对应的值。
     *
     * @param <T> 值的类型
     * @param key 键
     * @return 键对应的值
     */
    public <T> T getByKey(String key) {
        return Common.to(jwsClaims.getBody().get(key));
    }

    /**
     * 创建 JwtToken 解密构建器。
     *
     * @return JwtToken 解密构建器
     */
    public static JwtDecryptBuilder decryptBuilder() {
        return new JwtDecryptBuilder();
    }

    /**
     * JwtToken 解密构建器
     */
    public static class JwtDecryptBuilder {

        // 密钥
        private String secret;

        // 需要解密的 Token
        private String token;

        // 密钥算法
        private SignatureAlgorithm signatureAlgorithm;

        /**
         * 设置需要解密的 Token。
         *
         * @param token Token
         * @return JwtDecryptBuilder
         */
        public JwtDecryptBuilder token(String token) {
            this.token = token;
            return this;
        }

        /**
         * 设置解密密钥。
         *
         * @param secret 密钥
         * @return JwtDecryptBuilder
         */
        public JwtDecryptBuilder secret(String secret) {
            this.secret = secret;
            return this;
        }

        /**
         * 设置密钥算法。
         *
         * @param signatureAlgorithm 密钥算法
         * @return JwtDecryptBuilder
         */
        public JwtDecryptBuilder signatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
            this.signatureAlgorithm = signatureAlgorithm;
            return this;
        }

        /**
         * 构建 JwtDecryptHelper 对象。
         *
         * @return JwtDecryptHelper 对象
         */
        public JwtDecryptHelper build() {
            return new JwtDecryptHelper(this.token, this.secret, this.signatureAlgorithm);
        }
    }
}
