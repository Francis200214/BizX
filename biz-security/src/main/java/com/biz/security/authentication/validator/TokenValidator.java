package com.biz.security.authentication.validator;

/**
 * TokenValidator 接口，定义验证 Token 的标准方法。
 *
 * <p>
 * 提供用于验证 Token 是否有效的接口方法。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-09
 */
public interface TokenValidator {

    /**
     * 验证 Token 是否有效。
     *
     * @param token 要验证的 Token
     * @return {@code true} 如果 Token 有效，否则返回 {@code false}
     */
    boolean validate(String token);

}
