package com.biz.common.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间转换
 *
 * @author francis
 * @create 2024-06-22 11:57
 **/
public class BizDateFormat {

    private static final ThreadLocal<Map<String, DateFormat>> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<>();


    public static DateFormat getDateFormat(String format) {
        Map<String, DateFormat> dateFormatMap = DATE_FORMAT_THREAD_LOCAL.get();
        if (dateFormatMap == null) {
            dateFormatMap = new HashMap<>();
            dateFormatMap.put(format, new SimpleDateFormat(format));
            DATE_FORMAT_THREAD_LOCAL.set(dateFormatMap);
        }

        if (!dateFormatMap.containsKey(format)) {
            dateFormatMap.put(format, new SimpleDateFormat(format));
            DATE_FORMAT_THREAD_LOCAL.set(dateFormatMap);
        }

        return DATE_FORMAT_THREAD_LOCAL.get().get(format);
    }
}
