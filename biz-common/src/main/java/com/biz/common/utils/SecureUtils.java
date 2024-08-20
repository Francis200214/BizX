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
 * @version 1.0.1
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
}
