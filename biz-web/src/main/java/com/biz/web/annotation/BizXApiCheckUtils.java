package com.biz.web.annotation;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * ApiCheck Utils
 *
 * @author francis
 * @create: 2023-04-08 15:33
 **/
public class BizXApiCheckUtils {

    public static  Class<? extends Annotation> caseAnnotation(Class<?> clazz) {
        if (clazz.equals(String.class)) {
            return BizXApiCheckString.class;
        } else if (clazz.equals(Double.class)) {
            return BizXApiCheckDouble.class;
        } else if (clazz.equals(Integer.class)) {
            return BizXApiCheckInteger.class;
        } else if (clazz.equals(Long.class)) {
            return BizXApiCheckLong.class;
        } else if (clazz.equals(Collection.class)) {
            return BizXApiCheckList.class;
        } else if (clazz.equals(Float.class)) {
            return BizXApiCheckFloat.class;
        } else if (clazz.equals(Short.class)) {
            return BizXApiCheckShort.class;
        } else {
            return null;
        }
    }

}
