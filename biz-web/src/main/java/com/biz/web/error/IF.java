package com.biz.web.error;

import java.util.function.Supplier;

/**
 * 异常校验器
 *
 * @author francis
 * @since 2023-05-08 21:06
 **/
public class IF {

    public static <T> void isNull(T t, ErrorBaseInterface e) {
        if (t == null) {
            throw new BizXException(e);
        }
    }

    public static <T> void isNull(T t, int code, String message) {
        if (t == null) {
            throw new BizXException(code, message);
        }
    }

    public static <T> void isNull(T t, String message) {
        if (t == null) {
            throw new BizXException(message);
        }
    }

    public static <T> void isNotNull(T t, ErrorBaseInterface e) {
        if (t == null) {
            throw new BizXException(e);
        }
    }

    public static <T> void isNotNull(T t, int code, String message) {
        if (t == null) {
            throw new BizXException(code, message);
        }
    }

    public static <T> void isNotNull(T t, String message) {
        if (t == null) {
            throw new BizXException(message);
        }
    }

    public static void is(boolean check, ErrorBaseInterface e) {
        if (check) {
            throw new BizXException(e);
        }
    }

    public static void is(boolean check, int code, String message) {
        if (check) {
            throw new BizXException(code, message);
        }
    }

    public static void is(boolean check, String message) {
        if (check) {
            throw new BizXException(message);
        }
    }

    public static void is(Supplier<Boolean> supplier, ErrorBaseInterface e) {
        if (supplier.get()) {
            throw new BizXException(e);
        }
    }

    public static void is(Supplier<Boolean> supplier, int code, String message) {
        if (supplier.get()) {
            throw new BizXException(code, message);
        }
    }

    public static void is(Supplier<Boolean> supplier, String message) {
        if (supplier.get()) {
            throw new BizXException(message);
        }
    }


}
