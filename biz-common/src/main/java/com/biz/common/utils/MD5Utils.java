package com.biz.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类，提供字符串和文件的MD5加密计算功能。
 * <p>
 * 该类封装了常用的MD5加密操作，包括对字符串和文件进行MD5哈希值的计算。
 * 结果均以32位小写十六进制字符串形式表示，适用于数据完整性校验和数据签名等场景。
 * </p>
 *
 * <pre>{@code
 * // 示例用法
 * String text = "Hello, World!";
 * String md5Hash = MD5Utils.computeMD5(text);
 * System.out.println("MD5 Hash: " + md5Hash);
 *
 * File file = new File("path/to/file.txt");
 * String fileMd5Hash = MD5Utils.computeFileMD5(file);
 * System.out.println("File MD5 Hash: " + fileMd5Hash);
 * }</pre>
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
public class MD5Utils {

    /**
     * MD5算法标识
     */
    private static final String MD5 = "MD5";

    /**
     * 计算字符串的MD5哈希值。
     * <p>
     * 该方法接受一个字符串作为输入，并计算其MD5哈希值。
     * 返回值为32位小写十六进制字符串形式的哈希值。
     * </p>
     *
     * @param inputText 待加密的字符串。
     * @return 字符串的MD5哈希值，以32位小写十六进制字符串形式表示。
     */
    public static String computeMD5(String inputText) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] messageDigest = md.digest(inputText.getBytes());
            return bytesToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 加密过程中出现异常，无法获取MD5 MessageDigest实例", e);
        }
    }

    /**
     * 计算文件的MD5哈希值。
     * <p>
     * 该方法接受一个文件对象作为输入，并计算其MD5哈希值。
     * 适用于文件完整性校验，返回值为32位小写十六进制字符串形式的哈希值。
     * </p>
     *
     * @param file 待计算哈希值的文件。
     * @return 文件的MD5哈希值，以32位小写十六进制字符串形式表示。
     */
    public static String computeFileMD5(File file) {
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
     * <p>
     * 该方法将给定的字节数组转换为对应的十六进制字符串表示形式，
     * 主要用于MD5哈希值的转换。
     * </p>
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
