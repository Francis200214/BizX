package com.biz.common.utils;



/**
 * 安全摘要算法工具类
 *
 * @author francis
 */
public final class SecureUtils {

    public static String toMd5(String dateString) {
        return MD5Util.getMD5(dateString);
    }

}
