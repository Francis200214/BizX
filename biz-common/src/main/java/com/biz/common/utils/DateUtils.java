package com.biz.common.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 基础时间工具类
 *
 * @author francis
 */
public final class DateUtils {

    public static final String DEFAULT_DATE = "yyyy-MM-dd";
    public static final String DEFAULT_TIME = "HH:mm:ss";
    public static final String DEFAULT_DATETIME = DEFAULT_DATE + " " + DEFAULT_TIME;
    public static final String DEFAULT_YEAR = "yyyy";
    public static final String DEFAULT_MM = "MM";


    public static String format(Calendar cal) {
        return format(cal, DEFAULT_DATETIME);
    }

    public static String format(Calendar cal, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(BizCommon.isBlank(format) ? DEFAULT_DATETIME : format);
        return simpleDateFormat.format(cal.getTime());
    }

}
