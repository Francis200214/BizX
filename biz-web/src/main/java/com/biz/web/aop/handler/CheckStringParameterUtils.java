package com.biz.web.aop.handler;

import com.biz.web.annotation.BizXApiCheckString;

/**
 * 处理 String 类型参数工具类
 *
 * @author francis
 * @create: 2023-04-09 22:57
 **/
public class CheckStringParameterUtils {

    public static void checkIsNull(BizXApiCheckString annotation, Object o) {
        if (annotation.isNull()) {
            return;
        }

        if (o == null) {
            throw new RuntimeException("");
        }
    }

}
