package com.biz.common.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 时间转换工具类，提供线程安全的日期格式化。
 *
 * @author francis
 * @create 2024-06-22 11:57
 **/
public class BizDateFormat {

    // 使用 ThreadLocal 来确保每个线程有一个独立的 DateFormat 实例
    private static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            // 为每个线程初始化时创建 SimpleDateFormat 实例
            return new SimpleDateFormat();
        }
    };

    /**
     * 获取线程安全的 DateFormat 实例。
     * 注意：此方法假定调用者将使用相同的格式字符串来获取 DateFormat 实例。
     * 如果需要支持多种格式，应相应调整实现。
     *
     * @return 线程安全的 DateFormat 实例。
     */
    public static DateFormat getDateFormat() {
        return DATE_FORMAT_THREAD_LOCAL.get();
    }

    /**
     * 为特定格式字符串初始化并获取线程安全的 DateFormat 实例。
     * 此方法解决了多线程环境下 SimpleDateFormat 的非线程安全问题，
     * 通过 ThreadLocal 隔离每个线程的 DateFormat 实例。
     *
     * @param format 日期格式字符串，如 "yyyy-MM-dd HH:mm:ss"。
     * @return 线程安全的 DateFormat 实例，针对特定格式字符串。
     */
    public static DateFormat getDateFormat(String format) {
        // 使用 ThreadLocal 来确保每个线程和每个格式有一个独立的 DateFormat 实例
        ThreadLocal<DateFormat> formatThreadLocal = new ThreadLocal<DateFormat>() {
            @Override
            protected DateFormat initialValue() {
                return new SimpleDateFormat(format);
            }
        };

        return formatThreadLocal.get();
    }
}
