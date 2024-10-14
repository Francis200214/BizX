package com.biz.security.authentication.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 使用 SHA-256 算法实现密码加密和验证的类。
 *
 * <p>
 * 此类实现了 {@link PasswordEncryptor} 接口，提供对密码的加密和验证功能。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
public class SHA256PasswordEncryptor implements PasswordEncryptor {

    /**
     * 使用 SHA-256 算法加密密码。
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    @Override
    public String encrypt(String rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(rawPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256加密失败", e);
        }
    }

    /**
     * 验证密码是否匹配。
     *
     * @param rawPassword 用户输入的明文密码
     * @param encryptedPassword 已加密的密码
     * @return {@code true} 如果密码匹配，否则返回 {@code false}
     */
    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return encrypt(rawPassword).equals(encryptedPassword);
    }
}
