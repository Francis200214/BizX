package com.biz.common.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础时间工具类
 *
 * @author francis
 */
public final class DateTimeUtils {

    public static final String DEFAULT_DATE = "yyyy-MM-dd";
    public static final String DEFAULT_TIME = "HH:mm:ss";
    public static final String DEFAULT_DATETIME = DEFAULT_DATE + " " + DEFAULT_TIME;
    public static final String DEFAULT_YEAR = "yyyy";
    public static final String DEFAULT_MM = "MM";

    private static final ThreadLocal<Map<String, DateFormat>> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<>();


    /**
     * Calendar 转成时间字符串
     * 格式：yyyy-MM-dd HH:mm:ss
     *
     * @param cal
     * @return
     */
    public static String calendarToStr(Calendar cal) {
        return calendarToStr(cal, DEFAULT_DATETIME);
    }


    /**
     * Calendar 转成时间字符串
     *
     * @param cal
     * @param format 时间格式
     * @return
     */
    public static String calendarToStr(Calendar cal, String format) {
        return getDateFormat(Common.isBlank(format) ? DEFAULT_DATETIME : format).format(cal.getTime());
    }


    /**
     * Date 转成 时间字符串
     * 格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        return getDateFormat(DEFAULT_DATETIME).format(date);
    }

    /**
     * Date 转成 时间字符串
     *
     * @param date   Date
     * @param format 格式
     * @return
     */
    public static String dateToStr(Date date, String format) {
        return getDateFormat(format).format(date);
    }

    /**
     * 时间字符串转成时间
     *
     * @param date 时间字符串
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String date) throws ParseException {
        return getDateFormat(DEFAULT_DATETIME).parse(date);
    }

    /**
     * 时间字符串转成时间
     *
     * @param date  时间字符串
     * @param parse 转换格式
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String date, String parse) throws ParseException {
        return getDateFormat(parse).parse(date);
    }

    /**
     * 根据时间戳或者Date时间
     *
     * @param timeMills
     * @return
     */
    public static String longToDateStr(long timeMills) {
        return getDateFormat(DEFAULT_DATETIME).format(getCalendar(timeMills).getTime());
    }

    /**
     * 获取 DateFormat
     *
     * @param format 时间转换格式
     * @return
     */
    private static DateFormat getDateFormat(String format) {
        Map<String, DateFormat> dateFormatMap = DATE_FORMAT_THREAD_LOCAL.get();
        if (DATE_FORMAT_THREAD_LOCAL.get() == null) {
            Map<String, DateFormat> map = new HashMap<>();
            map.put(format, new SimpleDateFormat(format));
            DATE_FORMAT_THREAD_LOCAL.set(map);
        }

        if (!dateFormatMap.containsKey(format)) {
            dateFormatMap.put(format, new SimpleDateFormat(format));
            DATE_FORMAT_THREAD_LOCAL.set(dateFormatMap);
        }

        return DATE_FORMAT_THREAD_LOCAL.get().get(format);
    }


    /**
     * 根据时间戳初始化 Calendar
     *
     * @param timeMills
     * @return
     */
    public static Calendar getCalendar(long timeMills) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMills);
        return cal;
    }

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
