package com.biz.common.utils;

import com.biz.common.date.datetime.DateTimeUtils;
import com.biz.common.random.PhoneNumberUtil;
import com.biz.common.random.RandomUtils;

import java.util.Collection;

/**
 * 提供常用工具方法的类。
 * 包含对字符串、集合、日期时间及随机数的处理功能。
 *
 * @author francis
 * @date 2023/3/28 18:43
 */
public final class Common {

    /**
     * 将变长参数数组转换为double类型的数组。
     * 该方法主要用于处理变长参数，避免直接使用变长参数的限制。
     *
     * @param doubles 变长参数double数组
     * @return double类型的数组
     */
    public static double[] toDoubles(double... doubles) {
        return doubles;
    }

    /**
     * 判断字符串是否不为空且不全为空格。
     * 为空字符串或者null返回false，否则返回true。
     *
     * @param str 待检查的字符串
     * @return 字符串不为空且不全为空格时返回true，否则返回false
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断字符串是否为空或全为空格。
     * 为空字符串、null或者全为空格返回true，否则返回false。
     *
     * @param str 待检查的字符串
     * @return 字符串为空或全为空格时返回true，否则返回false
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断集合是否为空。
     * 集合为null或者为空集合返回true，否则返回false。
     *
     * @param collection 待检查的集合
     * @return 集合为空时返回true，否则返回false
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 安全地将对象转换为指定类型。
     * 该方法主要用于泛型的类型转换，确保类型安全。
     *
     * @param o 待转换的对象
     * @param <T> 目标类型
     * @return 转换后的对象
     */
    public static <T> T to(Object o) {
        return (T) o;
    }

    /**
     * 获取当前时间的字符串表示。
     * 格式为"yyyy-MM-dd HH:mm:ss"。
     *
     * @return 当前时间的字符串表示
     */
    public static String now() {
        return DateTimeUtils.longToDateStr(System.currentTimeMillis());
    }

    /**
     * 获取当前时间的字符串表示，根据指定格式。
     *
     * @param format 时间格式
     * @return 按指定格式格式化后的当前时间字符串
     */
    public static String now(String format) {
        return DateTimeUtils.longToDateStr(System.currentTimeMillis(), format);
    }

    /**
     * 生成一个随机的手机号码。
     * 使用指定的运营商生成一个随机的11位手机号码。
     *
     * @return 随机的手机号码
     */
    public static String createRandomPhoneNumber() {
        return PhoneNumberUtil.createPhoneNumber(
                PhoneNumberUtil.OperatorEnum.getOperateByCode(
                        RandomUtils.generateNumber(3)));
    }
}
