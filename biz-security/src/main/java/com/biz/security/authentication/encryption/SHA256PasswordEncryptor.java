package com.biz.security.authentication.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 使用SHA-256算法实现密码加密和验证的类
 *
 * @author francis
 * @create 2024-09-20
 * @since 1.0.1
 **/
public class SHA256PasswordEncryptor implements PasswordEncryptor {

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

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return encrypt(rawPassword).equals(encryptedPassword);
    }

}
