package com.biz.security.demo.config;

import com.biz.common.jwt.JwtDecryptHelper;
import com.biz.security.authentication.validator.TokenValidator;
import org.springframework.stereotype.Component;

/**
 * Token 认证校验
 *
 * @author francis
 * @create 2024-10-11
 * @since 1.0.1
 **/
@Component
public class TokenValidatorImpl implements TokenValidator {

    @Override
    public boolean validate(String token) {
        JwtDecryptHelper build = JwtDecryptHelper.decryptBuilder()
                .token(token)
                .secret("secret_secret_secret_secret")
                .build();
        if (build == null) {
            return false;
        }
        try {
            Object userId = build.getByKey("userId");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
