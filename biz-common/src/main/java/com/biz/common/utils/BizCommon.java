package com.biz.common.utils;


import cn.hutool.core.util.StrUtil;

/**
 * 公共工具类
 *
 * @author francis
 */
public final class BizCommon {

    public static boolean isBlank(String s) {
        return StrUtil.isBlank(s);
    }

    public static boolean isNotBlank(String s) {
        return StrUtil.isNotBlank(s);
    }

    public static <T> T to(Object object) {
        return (T) object;
    }

}
