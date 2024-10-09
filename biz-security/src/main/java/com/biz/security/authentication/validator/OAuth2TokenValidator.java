package com.biz.security.authentication.validator;

/**
 * OAuth2TokenValidator接口，定义验证OAuth2 Token的标准方法。
 *
 * <p>不同的OAuth2提供商可以有不同的实现方式。</p>
 *
 * @author francis
 * @create 2024-10-09
 **/
public interface OAuth2TokenValidator {

    /**
     * 验证OAuth2 Token是否有效
     *
     * @param provider OAuth2提供商名称（如Google、Facebook等）
     * @param token OAuth2 Token
     * @return 是否通过验证
     */
    boolean validate(String provider, String token);

}
