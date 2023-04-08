package com.biz.common.utils;

/**
 * Class 工具类
 *
 * @author francis
 * @create: 2023-04-08 10:52
 **/
public final class ClassUtils {

    /**
     * 判断是否是包装类型
     *
     * @param clazz
     * @return
     */
    public static boolean isPackagingType(Class<?> clazz) {
        return clazz.isPrimitive();
    }

    /**
     * 是否是字符串类型
     *
     * @param clazz
     * @return
     */
    public static boolean isStringClass(Class<?> clazz) {
        return clazz == String.class;
    }

}
