package com.biz.common.date.calendar;

import com.biz.common.date.DateConstant;
import com.biz.common.date.datetime.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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


    /**
     * 判断 Calendar 对象是否符合指定的时间格式。
     *
     * @param calendar 需要检查的 Calendar 对象
     * @param format   指定的时间格式，例如 "yyyy-MM-dd"
     * @return 如果 Calendar 对象符合指定的时间格式，返回 true；否则返回 false
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
