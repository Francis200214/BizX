package com.biz.common.date.calendar;

import com.biz.common.date.DateConstant;
import com.biz.common.date.datetime.DateTimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Calendar 时间工具类
 *
 * @author francis
 */
public final class CalendarUtils {

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DateConstant.DEFAULT_DATE);
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DateConstant.DEFAULT_DATETIME);

    /**
     * 获取去年
     * 【时间格式：yyyy】
     *
     * @return
     */
    public static String currentYear() {
        return String.valueOf(LocalDate.now().getYear());
    }

    /**
     * 获取今年开始时间
     * 【时间格式：yyyy-MM-dd HH:mm:ss】
     *
     * @return
     */
    public static String currentYearStartDateTime() {
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        return LocalDateTime.of(startOfYear, LocalTime.MIN).format(DEFAULT_DATE_TIME_FORMATTER);
    }

    /**
     * 获取今年结束时间
     * 【时间格式：yyyy-MM-dd HH:mm:ss】
     *
     * @return
     */
    public static String currentYearEndDateTime() {
        LocalDate endOfYear = LocalDate.now().withDayOfYear(LocalDate.now().isLeapYear() ? 366 : 365);
        return LocalDateTime.of(endOfYear, LocalTime.MAX).format(DEFAULT_DATE_TIME_FORMATTER);
    }

    /**
     * 获取去年
     * 【时间格式：yyyy】
     *
     * @return
     */
    public static String lastYear() {
        return String.valueOf(LocalDate.now().minusYears(1).getYear());
    }

    /**
     * 获取去年开始时间
     * 【时间格式：yyyy-MM-dd HH:mm:ss】
     *
     * @return
     */
    public static String lastYearStartDateTime() {
        LocalDate startOfYear = LocalDate.now().minusYears(1).withDayOfYear(1);
        return LocalDateTime.of(startOfYear, LocalTime.MIN).format(DEFAULT_DATE_TIME_FORMATTER);
    }

    /**
     * 获取去年结束时间
     * 【时间格式：yyyy-MM-dd HH:mm:ss】
     *
     * @return
     */
    public static String lastYearEndDateTime() {
        LocalDate endOfYear = LocalDate.now().minusYears(1).withDayOfYear(LocalDate.now().minusYears(1).isLeapYear() ? 366 : 365);
        return LocalDateTime.of(endOfYear, LocalTime.MAX).format(DEFAULT_DATE_TIME_FORMATTER);
    }

    /**
     * 获取当年的第一天
     */
    public static String getFirstOfYear() {
        LocalDate firstOfYear = LocalDate.now().withDayOfYear(1);
        return firstOfYear.format(DEFAULT_DATE_FORMATTER);
    }

    /**
     * 获取当年的最后一天
     */
    public static String getLastOfYear() {
        LocalDate lastOfYear = LocalDate.now().withDayOfYear(LocalDate.now().isLeapYear() ? 366 : 365);
        return lastOfYear.format(DEFAULT_DATE_FORMATTER);
    }

    /**
     * 获取当月的第一天
     */
    public static String getFirstOfMonth() {
        LocalDate firstOfMonth = LocalDate.now().withDayOfMonth(1);
        return firstOfMonth.format(DEFAULT_DATE_FORMATTER);
    }

    /**
     * 获取当月的最后一天
     */
    public static String getLastOfMonth() {
        LocalDate lastOfMonth = LocalDate.now().plusMonths(1).minusDays(1);
        return lastOfMonth.format(DEFAULT_DATE_FORMATTER);
    }

}
