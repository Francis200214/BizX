package com.biz.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类，提供字符串和文件的MD5加密计算功能。
 *
 * @author francis
 * @create 2024-05-28 11:07
 **/
public class MD5Util {

    /**
     * MD5算法标识
     */
    private static final String MD5 = "MD5";

    /**
     * 计算字符串的MD5哈希值。
     *
     * @param input 待加密的字符串。
     * @return 字符串的MD5哈希值，以32位小写十六进制字符串形式表示。
     */
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] messageDigest = md.digest(input.getBytes());
            return bytesToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 加密过程中出现异常，无法获取MD5 MessageDigest实例", e);
        }
    }

    /**
     * 计算文件的MD5哈希值。
     *
     * @param file 待计算哈希值的文件。
     * @return 文件的MD5哈希值，以32位小写十六进制字符串形式表示。
     */
    public static String getFileMD5(File file) {
        try (InputStream fis = Files.newInputStream(file.toPath())) {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] buffer = new byte[8192]; // 增大缓冲区大小
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            byte[] messageDigest = md.digest();
            return bytesToHex(messageDigest);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("MD5 加密过程中出现异常", e);
        }
    }


    /**
     * 将字节数组转换为十六进制字符串。
     *
     * @param bytes 待转换的字节数组。
     * @return 转换后的十六进制字符串。
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
