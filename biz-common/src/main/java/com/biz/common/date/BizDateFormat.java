package com.biz.common.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 时间转换工具类，提供线程安全的日期格式化。
 * <p>该类使用 {@link ThreadLocal} 来确保每个线程有一个独立的 {@link DateFormat} 实例，从而解决 {@link SimpleDateFormat} 在多线程环境中的非线程安全问题。</p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 *     DateFormat dateFormat = BizDateFormat.getDateFormat("yyyy-MM-dd");
 *     String formattedDate = dateFormat.format(new Date());
 * }</pre>
 *
 * <p>注意：此方法假定调用者将使用相同的格式字符串来获取 {@link DateFormat} 实例。如果需要支持多种格式，应相应调整实现。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-06-22 11:57
 * @see java.text.DateFormat
 * @see java.text.SimpleDateFormat
 * @see java.lang.ThreadLocal
 */
public final class BizDateFormat {

    /**
     * 使用 {@link ThreadLocal} 来确保每个线程有一个独立的 {@link DateFormat} 实例。
     */
    private static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            // 为每个线程初始化时创建 SimpleDateFormat 实例
            return new SimpleDateFormat();
        }
    };

    /**
     * 获取线程安全的 {@link DateFormat} 实例。
     * <p>注意：此方法假定调用者将使用相同的格式字符串来获取 {@link DateFormat} 实例。如果需要支持多种格式，应相应调整实现。</p>
     *
     * @return 线程安全的 {@link DateFormat} 实例
     */
    public static DateFormat getDateFormat() {
        return DATE_FORMAT_THREAD_LOCAL.get();
    }

    /**
     * 为特定格式字符串初始化并获取线程安全的 {@link DateFormat} 实例。
     * <p>此方法解决了多线程环境下 {@link SimpleDateFormat} 的非线程安全问题，通过 {@link ThreadLocal} 隔离每个线程的 {@link DateFormat} 实例。</p>
     *
     * @param format 日期格式字符串，如 "yyyy-MM-dd HH:mm:ss"
     * @return 线程安全的 {@link DateFormat} 实例，针对特定格式字符串
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
