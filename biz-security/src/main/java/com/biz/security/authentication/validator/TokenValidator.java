package com.biz.security.authentication.validator;

/**
 * TokenValidator接口，定义验证 Token 的标准方法。
 *
 * @author francis
 * @create 2024-10-09
 **/
public interface TokenValidator {

    /**
     * 验证 Token 是否有效
     *
     * @param token Token
     * @return 是否通过验证
     */
    boolean validate(String token);

}
