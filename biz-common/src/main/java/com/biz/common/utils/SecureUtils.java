package com.biz.common.utils;

/**
 * 安全摘要算法工具类。
 * 提供安全相关的摘要算法处理，如MD5加密。
 *
 * @author francis
 */
public final class SecureUtils {

    /**
     * 将字符串日期转换为MD5摘要。
     * 该方法用于对特定字符串进行MD5加密，生成固定长度的加密字符串。
     *
     * @param dateString 待加密的字符串日期。
     * @return 加密后的MD5字符串。
     */
    public static String toMd5(String dateString) {
        return MD5Util.getMD5(dateString);
    }

}
