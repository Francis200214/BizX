package com.biz.common.utils;


import cn.hutool.crypto.SecureUtil;

/**
 * 安全摘要算法工具类
 *
 * @author francis
 */
public final class SecureUtils {

    public static String toMd5(String dateString) {
        return SecureUtil.md5(dateString);
    }

}
