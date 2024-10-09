package com.biz.security.authentication.encryption;

/**
 * PasswordEncryptionService 提供密码加密和验证的服务。
 *
 * @author francis
 * @create 2024-09-20
 * @since 1.0.1
 **/
public class PasswordEncryptionService {

    /**
     * 密码加密器
     */
    private final PasswordEncryptor passwordEncryptor;

    /**
     * 构造函数，注入密码加密器
     *
     * @param passwordEncryptor 密码加密器
     */
    public PasswordEncryptionService(PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }

    /**
     * 加密密码
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    public String encryptPassword(String rawPassword) {
        return passwordEncryptor.encrypt(rawPassword);
    }

    /**
     * 验证密码
     *
     * @param rawPassword 用户输入的明文密码
     * @param encryptedPassword 存储在数据库中的加密密码
     * @return 是否匹配
     */
    public boolean matches(String rawPassword, String encryptedPassword) {
        return passwordEncryptor.matches(rawPassword, encryptedPassword);
    }

}
