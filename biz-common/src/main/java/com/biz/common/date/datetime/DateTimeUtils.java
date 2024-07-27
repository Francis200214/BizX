package com.biz.common.date.datetime;

import com.biz.common.date.BizDateFormat;
import com.biz.common.date.DateConstant;
import com.biz.common.date.calendar.CalendarUtils;
import com.biz.common.utils.Common;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 提供日期和时间相关的实用方法。
 *
 * @author francis
 */
public final class DateTimeUtils {

    /**
     * 将Calendar对象转换为默认格式的日期字符串（yyyy-MM-dd HH:mm:ss）。
     *
     * @param cal 要转换的Calendar对象
     * @return 默认格式的日期字符串
     */
    public static String calendarToStr(Calendar cal) {
        return calendarToStr(cal, DateConstant.DEFAULT_DATETIME);
    }

    /**
     * 将Calendar对象转换为指定格式的日期字符串。
     *
     * @param cal       要转换的Calendar对象
     * @param format    日期格式
     * @return 指定格式的日期字符串
     */
    public static String calendarToStr(Calendar cal, String format) {
        return BizDateFormat.getDateFormat(Common.isBlank(format) ? DateConstant.DEFAULT_DATETIME : format).format(cal.getTime());
    }

    /**
     * 将Date对象转换为默认格式的日期字符串（yyyy-MM-dd HH:mm:ss）。
     *
     * @param date 要转换的Date对象
     * @return 默认格式的日期字符串
     */
    public static String dateToStr(Date date) {
        return BizDateFormat.getDateFormat(DateConstant.DEFAULT_DATETIME).format(date);
    }

    /**
     * 将Date对象转换为指定格式的日期字符串。
     *
     * @param date   要转换的Date对象
     * @param format 日期格式
     * @return 指定格式的日期字符串
     */
    public static String dateToStr(Date date, String format) {
        return BizDateFormat.getDateFormat(format).format(date);
    }

    /**
     * 将日期字符串转换为Date对象，默认格式为yyyy-MM-dd HH:mm:ss。
     *
     * @param date 时间字符串
     * @return 解析后的Date对象
     * @throws ParseException 如果解析失败
     */
    public static Date strToDate(String date) throws ParseException {
        return BizDateFormat.getDateFormat(DateConstant.DEFAULT_DATETIME).parse(date);
    }

    /**
     * 将日期字符串转换为Date对象，指定格式。
     *
     * @param date  时间字符串
     * @param parse 日期格式
     * @return 解析后的Date对象
     * @throws ParseException 如果解析失败
     */
    public static Date strToDate(String date, String parse) throws ParseException {
        return BizDateFormat.getDateFormat(parse).parse(date);
    }

    /**
     * 将时间戳转换为默认格式的日期字符串（yyyy-MM-dd HH:mm:ss）。
     *
     * @param timeMills 时间戳
     * @return 默认格式的日期字符串
     */
    public static String longToDateStr(long timeMills) {
        return BizDateFormat.getDateFormat(DateConstant.DEFAULT_DATETIME).format(getCalendar(timeMills).getTime());
    }

    /**
     * 将时间戳转换为指定格式的日期字符串。
     *
     * @param timeMills 时间戳
     * @param parse     日期格式
     * @return 指定格式的日期字符串
     */
    public static String longToDateStr(long timeMills, String parse) {
        return BizDateFormat.getDateFormat(parse).format(getCalendar(timeMills).getTime());
    }

    /**
     * 根据时间戳创建一个Calendar对象。
     *
     * @param timeMills 时间戳
     * @return 创建的Calendar对象
     */
    public static Calendar getCalendar(long timeMills) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMills);
        return cal;
    }

    /**
     * 获取当前年份的字符串表示。
     *
     * @return 当前年份的字符串
     */
    public static String currentYear() {
        return CalendarUtils.currentYear();
    }

    /**
     * 获取本年度开始的日期时间字符串（yyyy-MM-dd HH:mm:ss）。
     *
     * @return 本年度开始的日期时间字符串
     */
    public static String currentYearStartDateTime() {
        return CalendarUtils.currentYearStartDateTime();
    }

    /**
     * 获取本年度结束的日期时间字符串（yyyy-MM-dd HH:mm:ss）。
     *
     * @return 本年度结束的日期时间字符串
     */
    public static String currentYearEndDateTime() {
        return CalendarUtils.currentYearEndDateTime();
    }

    /**
     * 获取去年的字符串表示。
     *
     * @return 去年的字符串
     */
    public static String lastYear() {
        return CalendarUtils.lastYear();
    }

    /**
     * 获取去年开始的日期时间字符串（yyyy-MM-dd HH:mm:ss）。
     *
     * @return 去年开始的日期时间字符串
     */
    public static String lastYearStartDateTime() {
        return CalendarUtils.lastYearStartDateTime();
    }

    /**
     * 获取去年结束的日期时间字符串（yyyy-MM-dd HH:mm:ss）。
     *
     * @return 去年结束的日期时间字符串
     */
    public static String lastYearEndDateTime() {
        return CalendarUtils.lastYearEndDateTime();
    }

    /**
     * 获取今年的第一天日期字符串。
     */
    public static String getFirstOfYear() {
        return CalendarUtils.getFirstOfYear();
    }

    /**
     * 获取今年的最后一天日期字符串。
     */
    public static String getLastOfYear() {
        return CalendarUtils.getLastOfYear();
    }

    /**
     * 获取当月的第一天日期字符串。
     */
    public static String getFirstOfMonth() {
        return CalendarUtils.getFirstOfMonth();
    }

    /**
     * 获取当月的最后一天日期字符串。
     */
    public static String getLastOfMonth() {
        return CalendarUtils.getLastOfMonth();
    }

}
