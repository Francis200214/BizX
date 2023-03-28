package com.biz.common.utils;

/**
 * 公共工具类
 *
 * @author francis
 * @date 2023/3/28 18:43
 */
public final class Common {


    /**
     * 判断字符串是否不为Null或字符串去除两端空格后长度是否不为0
     *
     * @param str 被判断字符串
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断字符串是否为Null或字符串去除两端空格后长度是否为0
     *
     * @param str 被判断字符串
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null ||  str.trim().length() == 0;
    }

    /**
     * Object 转换为 T
     *
     * @param o
     * @param <T>
     * @return
     */
    public static <T> T to(Object o) {
        return (T) o;
    }


}
