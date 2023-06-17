package com.biz.common.utils;

import com.biz.common.date.calendar.CalendarUtils;
import com.biz.common.date.datetime.DateTimeUtils;

import java.util.Collection;
import java.util.Date;

/**
 * 公共工具类
 * 该工具类主要整合一些常用的工具类方法，
 * 比如说操作时间转换、字符串判空、集合判空等
 *
 * @author francis
 * @date 2023/3/28 18:43
 */
public final class Common {


    public static double[] toDoubles(double... doubles) {
        int length = doubles.length;
        if (length == 0) {
            return new double[length];
        }
        double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            result[i] = doubles[i];
        }
        return result;
    }

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
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
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


    public static String now() {
        return DateTimeUtils.longToDateStr(System.currentTimeMillis());
    }

    public static String now(String format) {
        return DateTimeUtils.longToDateStr(System.currentTimeMillis(), format);
    }

}
