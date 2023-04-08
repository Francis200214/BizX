package com.biz.web.core;

/**
 * 入参校验工具类
 *
 * @author francis
 * @create: 2023-04-08 10:49
 **/
public class VerificationUtils {

    /**
     * 验证是否手机号
     *
     * @param phone
     * @return
     */
    public static Boolean isPhone(Object phone) {
        String pattern = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\\d{8})?$";
        return phone.toString().matches(pattern);
    }

    /**
     * 验证是否为空
     *
     * @param value
     * @return
     */
    public static Boolean isNull(Object value) {
        return value != null || String.valueOf(value).length() > 0;
    }

}
