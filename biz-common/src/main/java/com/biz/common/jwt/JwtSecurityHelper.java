package com.biz.common.jwt;

import com.biz.common.utils.Common;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;

/**
 * Jwt 与 Spring Security 之间融合的工具类
 * <p>该工具类提供了从 HttpServletRequest 中提取 JWT Token，并结合 Spring Security 对其进行解密和处理的功能。</p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 * JwtSecurityHelper helper = JwtSecurityHelper.jwtSecurityBuilder()
 *     .httpServletRequest(request)
 *     .secret("your-secret")
 *     .signatureAlgorithm(SignatureAlgorithm.HS256)
 *     .build();
 *
 * if (helper.isToken()) {
 *     String subject = helper.getSub();
 *     boolean isExpired = helper.isExpire();
 * }
 * }</pre>
 *
 * <p>该类依赖于 {@link JwtDecryptHelper} 和 {@link Common} 类提供的工具方法。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 **/
public final class JwtSecurityHelper {

    private static final String AUTHORIZATION_STR = "Authorization";
    private static final String BEARER_STR = "Bearer ";
    private final JwtDecryptHelper JWT_TOKEN_DECRYPT_HELPER;

    private final String TOKEN;

    /**
     * 私有构造函数，初始化 JwtSecurityHelper 对象。
     *
     * @param request            HttpServletRequest 对象，用于从请求头中提取 JWT Token。
     * @param secret             密钥，用于解密 JWT Token。
     * @param signatureAlgorithm 签名算法，用于验证 JWT Token。
     */
    private JwtSecurityHelper(HttpServletRequest request, String secret, SignatureAlgorithm signatureAlgorithm) {
        this.TOKEN = subJwtTokenFromRequest(request);
        this.JWT_TOKEN_DECRYPT_HELPER = JwtDecryptHelper.decryptBuilder()
                .token(this.TOKEN)
                .secret(secret)
                .signatureAlgorithm(signatureAlgorithm)
                .build();
    }

    /**
     * 检查 Token 是否过期。
     *
     * @return 如果 Token 已过期，返回 true；否则返回 false。
     */
    public boolean isExpire() {
        return JWT_TOKEN_DECRYPT_HELPER.isExpired();
    }

    /**
     * 检查是否存在 Token。
     *
     * @return 如果存在 Token，返回 true；否则返回 false。
     */
    public boolean isToken() {
        return Common.isNotBlank(TOKEN);
    }

    /**
     * 获取 Token 中的 Subject 信息。
     *
     * @return Token 中的 Subject 信息。
     */
    public String getSub() {
        return this.JWT_TOKEN_DECRYPT_HELPER.getSubject();
    }

    /**
     * 获取 Token 中指定 Key 对应的值。
     *
     * @param key 指定的键。
     * @return 键对应的值。
     */
    public String getDataByKey(String key) {
        return this.JWT_TOKEN_DECRYPT_HELPER.getByKey(key);
    }

    /**
     * 创建 JwtSecurityHelper 的构建器。
     *
     * @return JwtSecurityBuilder 实例。
     */
    public static JwtSecurityBuilder jwtSecurityBuilder() {
        return new JwtSecurityBuilder();
    }

    /**
     * 从 HttpServletRequest 中提取 JWT Token。
     *
     * @param request HttpServletRequest 对象。
     * @return 提取到的 Token，如果没有则返回 null。
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
     * Jwt Security 构建器，用于构建 JwtSecurityHelper 对象。
     */
    public static class JwtSecurityBuilder {

        /**
         * HttpServletRequest 对象。
         */
        private HttpServletRequest request;

        /**
         * 密钥，用于解密 JWT Token。
         */
        private String secret;

        /**
         * 签名算法，用于验证 JWT Token。
         */
        private SignatureAlgorithm signatureAlgorithm;

        /**
         * 设置 HttpServletRequest 对象。
         *
         * @param request HttpServletRequest 对象。
         * @return 当前构建器实例。
         */
        public JwtSecurityBuilder httpServletRequest(HttpServletRequest request) {
            this.request = request;
            return this;
        }

        /**
         * 设置解密密钥。
         *
         * @param secret 密钥。
         * @return 当前构建器实例。
         */
        public JwtSecurityBuilder secret(String secret) {
            this.secret = secret;
            return this;
        }

        /**
         * 设置签名算法。
         *
         * @param signatureAlgorithm 签名算法。
         * @return 当前构建器实例。
         */
        public JwtSecurityBuilder signatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
            this.signatureAlgorithm = signatureAlgorithm;
            return this;
        }

        /**
         * 构建 JwtSecurityHelper 对象。
         *
         * @return 构建的 JwtSecurityHelper 对象。
         */
        public JwtSecurityHelper build() {
            return new JwtSecurityHelper(request, this.secret, this.signatureAlgorithm);
        }
    }

}
