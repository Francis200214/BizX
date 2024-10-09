package com.biz.security.authentication.encryption;

import com.biz.common.utils.SecureUtils;

/**
 * MD5（不可逆）方式密码加密。
 *
 * @author francis
 * @create 2024-09-20
 * @since 1.0.1
 **/
public class MD5PasswordEncryptor implements PasswordEncryptor  {

    /**
     * 加密密码
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    @Override
    public String encrypt(String rawPassword) {
        return SecureUtils.toMd5(rawPassword);
    }

    /**
     * 验证密码
     *
     * @param rawPassword 用户输入的明文密码
     * @param encodedPassword 存储在数据库中的加密密码
     * @return 是否匹配
     */
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return SecureUtils.matchByMD5(rawPassword, encodedPassword);
    }


}
