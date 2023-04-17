package com.biz.common.utils;

import java.util.Calendar;

/**
 * Calendar 时间工具类
 *
 * @author francis
 */
public final class CalendarUtils {

    /**
     * 获取去年
     * 【时间格式：yyyy】
     *
     * @return
     */
    public static String currentYear() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 0);
        return DateTimeUtils.calendarToStr(cal, DateTimeUtils.DEFAULT_YEAR);
    }

    /**
     * 获取今年开始时间
     * 【时间格式：yyyy-MM-dd HH:mm:ss】
     *
     * @return
     */
    public static String currentYearStartDateTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 0);
        cal.add(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return DateTimeUtils.calendarToStr(cal);
    }

    /**
     * 获取今年结束时间
     * 【时间格式：yyyy-MM-dd HH:mm:ss】
     *
     * @return
     */
    public static String currentYearEndDateTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 0);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        cal.roll(Calendar.DAY_OF_YEAR, 0);
        return DateTimeUtils.calendarToStr(cal);
    }

    /**
     * 获取去年
     * 【时间格式：yyyy】
     *
     * @return
     */
    public static String lastYear() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        return DateTimeUtils.calendarToStr(cal, DateTimeUtils.DEFAULT_YEAR);
    }

    /**
     * 获取去年开始时间
     * 【时间格式：yyyy-MM-dd HH:mm:ss】
     *
     * @return
     */
    public static String lastYearStartDateTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.add(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return DateTimeUtils.calendarToStr(cal);
    }

    /**
     * 获取去年结束时间
     * 【时间格式：yyyy-MM-dd HH:mm:ss】
     *
     * @return
     */
    public static String lastYearEndDateTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        cal.roll(Calendar.DAY_OF_YEAR, 0);
        return DateTimeUtils.calendarToStr(cal);
    }

    /**
     * 获取当年的第一天
     */
    public static String getFirstOfYear() {
        Calendar currCal = Calendar.getInstance();
        currCal.set(Calendar.YEAR, currCal.get(Calendar.YEAR));
        currCal.add(Calendar.DATE, 1);
        currCal.add(Calendar.MONTH, 0);
        currCal.set(Calendar.DAY_OF_YEAR, 1);
        return DateTimeUtils.calendarToStr(currCal, DateTimeUtils.DEFAULT_DATE);
    }

    /**
     * 获取当年的最后一天
     */
    public static String getLastOfYear() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        currCal.set(Calendar.YEAR, currentYear);
        currCal.roll(Calendar.DAY_OF_YEAR, 0);
        return DateTimeUtils.calendarToStr(currCal, DateTimeUtils.DEFAULT_DATE);
    }

    /**
     * 获取当月的第一天
     */
    public static String getFirstOfMonth() {
        Calendar currCal = Calendar.getInstance();
        currCal.set(Calendar.DAY_OF_MONTH, 1);
        return DateTimeUtils.calendarToStr(currCal, DateTimeUtils.DEFAULT_DATE);
    }

    /**
     * 获取当月的最后一天
     */
    public static String getLastOfMonth() {
        Calendar currCal = Calendar.getInstance();
        currCal.roll(Calendar.DAY_OF_MONTH, 0);
        return DateTimeUtils.calendarToStr(currCal, DateTimeUtils.DEFAULT_DATE);
    }

}
