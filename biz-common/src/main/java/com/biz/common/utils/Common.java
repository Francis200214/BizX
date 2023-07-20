package com.biz.common.utils;

import com.biz.common.date.datetime.DateTimeUtils;
import com.biz.common.random.PhoneNumberUtil;
import com.biz.common.random.RandomUtils;

import java.util.Collection;

/**
 * 公共工具类
 * 该工具类主要整合一些常用的工具类方法，
 * 比如说操作时间转换、字符串判空、集合判空等
 *
 * @author francis
 * @date 2023/3/28 18:43
 */
public final class Common {


    /**
     * 多个 double 放入一个 double[] 中
     *
     * @param doubles 多个 double
     * @return double[]
     */
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

    /**
     * 获取当前时间【yyyy-MM-dd HH:mm:ss】
     *
     * @return 当前时间【yyyy-MM-dd HH:mm:ss】
     */
    public static String now() {
        return DateTimeUtils.longToDateStr(System.currentTimeMillis());
    }

    /**
     * 获取某种时间格式的当前时间
     *
     * @param format 时间格式
     * @return 某种时间格式的当前时间
     */
    public static String now(String format) {
        return DateTimeUtils.longToDateStr(System.currentTimeMillis(), format);
    }

    /**
     * 生成随机手机号
     *
     * @return 11位随机手机号
     */
    public static String createRandomPhoneNumber() {
        return PhoneNumberUtil.createPhoneNumber(
                PhoneNumberUtil.OperatorEnum.getOperateByCode(
                        RandomUtils.generateNumber(3)));
    }


}
