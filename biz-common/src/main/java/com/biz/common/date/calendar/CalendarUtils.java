package com.biz.common.date.calendar;

import com.biz.common.date.DateConstant;
import com.biz.common.date.datetime.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Calendar 时间工具类，提供了操作日期和时间的常用方法。
 * <p>该类使用了 {@link LocalDate} 和 {@link LocalDateTime} 等 Java 8 时间类来获取和格式化日期时间。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 * @see java.time.LocalDate
 * @see java.time.LocalDateTime
 * @see java.util.Calendar
 */
public final class CalendarUtils {

    /**
     * 默认的日期格式化器（格式：yyyy-MM-dd）。
     */
    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DateConstant.DEFAULT_DATE);

    /**
     * 默认的日期时间格式化器（格式：yyyy-MM-dd HH:mm:ss）。
     */
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DateConstant.DEFAULT_DATETIME);

    /**
     * 获取当前年份。
     * <p>时间格式：yyyy</p>
     *
     * @return 当前年份的字符串表示
     */
    public static String currentYear() {
        return String.valueOf(LocalDate.now().getYear());
    }

    /**
     * 获取今年开始时间。
     * <p>时间格式：yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 今年开始时间的字符串表示
     */
    public static String currentYearStartDateTime() {
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        return LocalDateTime.of(startOfYear, LocalTime.MIN).format(DEFAULT_DATE_TIME_FORMATTER);
    }

    /**
     * 获取今年结束时间。
     * <p>时间格式：yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 今年结束时间的字符串表示
     */
    public static String currentYearEndDateTime() {
        LocalDate endOfYear = LocalDate.now().withDayOfYear(LocalDate.now().isLeapYear() ? 366 : 365);
        return LocalDateTime.of(endOfYear, LocalTime.MAX).format(DEFAULT_DATE_TIME_FORMATTER);
    }

    /**
     * 获取去年年份。
     * <p>时间格式：yyyy</p>
     *
     * @return 去年年份的字符串表示
     */
    public static String lastYear() {
        return String.valueOf(LocalDate.now().minusYears(1).getYear());
    }

    /**
     * 获取去年开始时间。
     * <p>时间格式：yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 去年开始时间的字符串表示
     */
    public static String lastYearStartDateTime() {
        LocalDate startOfYear = LocalDate.now().minusYears(1).withDayOfYear(1);
        return LocalDateTime.of(startOfYear, LocalTime.MIN).format(DEFAULT_DATE_TIME_FORMATTER);
    }

    /**
     * 获取去年结束时间。
     * <p>时间格式：yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 去年结束时间的字符串表示
     */
    public static String lastYearEndDateTime() {
        LocalDate endOfYear = LocalDate.now().minusYears(1).withDayOfYear(LocalDate.now().minusYears(1).isLeapYear() ? 366 : 365);
        return LocalDateTime.of(endOfYear, LocalTime.MAX).format(DEFAULT_DATE_TIME_FORMATTER);
    }

    /**
     * 获取当年的第一天。
     * <p>时间格式：yyyy-MM-dd</p>
     *
     * @return 当年第一天的字符串表示
     */
    public static String getFirstOfYear() {
        LocalDate firstOfYear = LocalDate.now().withDayOfYear(1);
        return firstOfYear.format(DEFAULT_DATE_FORMATTER);
    }

    /**
     * 获取当年的最后一天。
     * <p>时间格式：yyyy-MM-dd</p>
     *
     * @return 当年最后一天的字符串表示
     */
    public static String getLastOfYear() {
        LocalDate lastOfYear = LocalDate.now().withDayOfYear(LocalDate.now().isLeapYear() ? 366 : 365);
        return lastOfYear.format(DEFAULT_DATE_FORMATTER);
    }

    /**
     * 获取当月的第一天。
     * <p>时间格式：yyyy-MM-dd</p>
     *
     * @return 当月第一天的字符串表示
     */
    public static String getFirstOfMonth() {
        LocalDate firstOfMonth = LocalDate.now().withDayOfMonth(1);
        return firstOfMonth.format(DEFAULT_DATE_FORMATTER);
    }

    /**
     * 获取当月的最后一天。
     * <p>时间格式：yyyy-MM-dd</p>
     *
     * @return 当月最后一天的字符串表示
     */
    public static String getLastOfMonth() {
        LocalDate lastOfMonth = LocalDate.now().plusMonths(1).minusDays(1);
        return lastOfMonth.format(DEFAULT_DATE_FORMATTER);
    }

    /**
     * 判断 {@link Calendar} 对象是否符合指定的时间格式。
     *
     * @param calendar 需要检查的 {@link Calendar} 对象，不能为空
     * @param format   指定的时间格式，例如 "yyyy-MM-dd"，不能为空
     * @return 如果 {@link Calendar} 对象符合指定的时间格式，返回 true；否则返回 false
     */
    public static boolean isValidFormat(Calendar calendar, String format) {
        if (calendar == null || format == null || format.isEmpty()) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        String formattedDate = sdf.format(calendar.getTime());

        try {
            Date parsedDate = sdf.parse(formattedDate);
            Calendar parsedCalendar = Calendar.getInstance();
            parsedCalendar.setTime(parsedDate);

            return calendar.get(Calendar.YEAR) == parsedCalendar.get(Calendar.YEAR) &&
                    calendar.get(Calendar.MONTH) == parsedCalendar.get(Calendar.MONTH) &&
                    calendar.get(Calendar.DAY_OF_MONTH) == parsedCalendar.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            return false;
        }
    }
}
