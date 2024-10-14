package com.biz.security.authentication.encryption;

import com.biz.common.utils.SecureUtils;

/**
 * 使用 MD5 算法进行不可逆密码加密的实现类。
 *
 * <p>
 * 此类实现了 {@link PasswordEncryptor} 接口，使用 MD5 算法对密码进行加密和验证。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
public class MD5PasswordEncryptor implements PasswordEncryptor {

    /**
     * 加密密码。
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    @Override
    public String encrypt(String rawPassword) {
        return SecureUtils.toMd5(rawPassword);
    }

    /**
     * 验证密码。
     *
     * @param rawPassword 用户输入的明文密码
     * @param encodedPassword 存储在数据库中的加密密码
     * @return {@code true} 如果密码匹配，否则返回 {@code false}
     */
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return SecureUtils.matchByUpperCaseMD5(rawPassword, encodedPassword);
    }
}
