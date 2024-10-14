package com.biz.security.authentication.encryption;

/**
 * PasswordEncryptionService 提供密码加密和验证的服务。
 *
 * <p>
 * 使用 {@link PasswordEncryptor} 来执行密码的加密和验证操作，开发者可以根据需要注入不同的加密实现。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
public class PasswordEncryptionService {

    /**
     * 密码加密器。
     */
    private final PasswordEncryptor passwordEncryptor;

    /**
     * 构造函数，注入密码加密器。
     *
     * @param passwordEncryptor 密码加密器
     */
    public PasswordEncryptionService(PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }

    /**
     * 加密密码。
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    public String encryptPassword(String rawPassword) {
        return passwordEncryptor.encrypt(rawPassword);
    }

    /**
     * 验证密码。
     *
     * @param rawPassword 用户输入的明文密码
     * @param encryptedPassword 存储在数据库中的加密密码
     * @return {@code true} 如果密码匹配，否则返回 {@code false}
     */
    public boolean matches(String rawPassword, String encryptedPassword) {
        return passwordEncryptor.matches(rawPassword, encryptedPassword);
    }
}
