package com.biz.security.authentication.validator;

/**
 * OAuth2TokenValidator 接口，定义验证 OAuth2 Token 的标准方法。
 *
 * <p>
 * 不同的 OAuth2 提供商可以有不同的实现方式。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-09
 */
public interface OAuth2TokenValidator {

    /**
     * 验证 OAuth2 Token 是否有效。
     *
     * @param provider OAuth2 提供商名称（如 Google、Facebook 等）
     * @param token    OAuth2 Token
     * @return {@code true} 如果 Token 有效，否则返回 {@code false}
     */
    boolean validate(String provider, String token);

}
