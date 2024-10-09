package com.biz.security.authentication.encryption;

/**
 * PasswordEncryptor 接口，定义通用的密码加密和验证逻辑。
 *
 * <p>
 *     开发者可以实现自己的加密方式，也可以使用框架提供的默认实现。
 * </p>
 *
 * @author francis
 * @create 2024-09-20
 * @since 1.0.1
 **/
public interface PasswordEncryptor {

    /**
     * 加密密码
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    String encrypt(String rawPassword);

    /**
     * 验证密码
     * @param rawPassword 明文密码
     * @param encryptedPassword 已加密的密码
     * @return 是否匹配
     */
    boolean matches(String rawPassword, String encryptedPassword);


}
