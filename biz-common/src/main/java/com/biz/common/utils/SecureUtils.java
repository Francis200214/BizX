package com.biz.common.utils;

/**
 * 安全摘要算法工具类。
 * <p>
 * 该类提供了安全相关的摘要算法处理方法，例如MD5加密。通过封装常见的加密操作，
 * 提供统一的接口，简化了安全处理的使用。
 * </p>
 *
 * @author francis
 * @since 1.0.1
 * @create 2023-03-17
 */
public final class SecureUtils {

    /**
     * 将字符串转换为MD5摘要。
     * <p>
     * 该方法用于对指定的字符串进行MD5加密，生成固定长度的加密字符串。MD5摘要广泛用于数据完整性校验和安全传输等场景。
     * </p>
     *
     * @param input 待加密的字符串。
     * @return 加密后的MD5字符串。
     */
    public static String toMd5(String input) {
        return MD5Utils.computeMD5(input);
    }

    /**
     * 校验字符串是否与MD5摘要匹配。
     * <p>
     * 该方法用于校验字符串是否与指定的MD5摘要匹配。通过比较两个字符串是否相等，可以确定字符串是否被篡改过。
     * </p>
     *
     * @param input 待校验的字符串。
     * @param md5   对比的MD5摘要。
     * @return 如果字符串与MD5摘要匹配，则返回true，否则返回false。
     */
    public static boolean matchByMD5(String input, String md5) {
        return MD5Utils.computeMD5(input).equals(md5);
    }

    /**
     * 校验字符串是否与MD5摘要（大写）匹配。
     * <p>
     * 该方法用于校验字符串是否与指定的MD5摘要（大写）匹配。通过比较两个字符串是否相等，可以确定字符串是否被篡改过。
     * </p>
     *
     * @param input 待校验的字符串。
     * @param md5   对比的MD5摘要（大写）。
     * @return 如果字符串与MD5摘要（大写）匹配，则返回true，否则返回false。
     */
    public static boolean matchByUpperCaseMD5(String input, String md5) {
        return MD5Utils.computeMD5(input).toUpperCase().equals(md5);
    }

}
