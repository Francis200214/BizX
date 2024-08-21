package com.biz.web.error;

import java.util.function.Supplier;

/**
 * {@code IF} 是一个异常校验器工具类，提供了一系列静态方法用于在程序中进行条件检查，
 * 如果条件满足则抛出自定义异常 {@link BizXWebException}。
 * <p>
 * 该类主要用于在代码中进行常见的非空检查或条件判断，从而在满足特定条件时抛出相应的业务异常。
 * 这些方法可以帮助简化代码中的异常处理逻辑，确保代码的可读性和可维护性。
 *
 * <h3>常见用法:</h3>
 * <pre>{@code
 * String username = getUsername();
 * IF.isNull(username, ErrorCode.NOT_LOGIN);
 *
 * boolean isInvalid = someValidation();
 * IF.is(isInvalid, ErrorCode.INVALID_REQUEST);
 * }</pre>
 *
 * <p>通过这种方式，开发者可以在应用程序中轻松地进行各种条件校验，并统一管理异常的抛出。</p>
 *
 * @see BizXWebException
 * @see ErrorBaseInterface
 * @see Supplier
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
public class IF {

    /**
     * 检查对象是否为 {@code null}，如果是，则抛出指定的异常。
     *
     * @param t 要检查的对象
     * @param e 异常接口，提供异常代码和消息
     * @param <T> 对象的类型
     * @throws BizXWebException 如果对象为 {@code null}
     */
    public static <T> void isNull(T t, ErrorBaseInterface e) {
        if (t == null) {
            throw new BizXWebException(e);
        }
    }

    /**
     * 检查对象是否为 {@code null}，如果是，则抛出指定的异常。
     *
     * @param t 要检查的对象
     * @param code 异常代码
     * @param message 异常消息
     * @param <T> 对象的类型
     * @throws BizXWebException 如果对象为 {@code null}
     */
    public static <T> void isNull(T t, int code, String message) {
        if (t == null) {
            throw new BizXWebException(code, message);
        }
    }

    /**
     * 检查对象是否为 {@code null}，如果是，则抛出带有指定消息的异常。
     *
     * @param t 要检查的对象
     * @param message 异常消息
     * @param <T> 对象的类型
     * @throws BizXWebException 如果对象为 {@code null}
     */
    public static <T> void isNull(T t, String message) {
        if (t == null) {
            throw new BizXWebException(message);
        }
    }

    /**
     * 检查对象是否不为 {@code null}，如果不是，则抛出指定的异常。
     *
     * @param t 要检查的对象
     * @param e 异常接口，提供异常代码和消息
     * @param <T> 对象的类型
     * @throws BizXWebException 如果对象不为 {@code null}
     */
    public static <T> void isNotNull(T t, ErrorBaseInterface e) {
        if (t != null) {
            throw new BizXWebException(e);
        }
    }

    /**
     * 检查对象是否不为 {@code null}，如果不是，则抛出指定的异常。
     *
     * @param t 要检查的对象
     * @param code 异常代码
     * @param message 异常消息
     * @param <T> 对象的类型
     * @throws BizXWebException 如果对象不为 {@code null}
     */
    public static <T> void isNotNull(T t, int code, String message) {
        if (t != null) {
            throw new BizXWebException(code, message);
        }
    }

    /**
     * 检查对象是否不为 {@code null}，如果不是，则抛出带有指定消息的异常。
     *
     * @param t 要检查的对象
     * @param message 异常消息
     * @param <T> 对象的类型
     * @throws BizXWebException 如果对象不为 {@code null}
     */
    public static <T> void isNotNull(T t, String message) {
        if (t != null) {
            throw new BizXWebException(message);
        }
    }

    /**
     * 检查布尔值是否为 {@code true}，如果是，则抛出指定的异常。
     *
     * @param check 要检查的布尔值
     * @param e 异常接口，提供异常代码和消息
     * @throws BizXWebException 如果布尔值为 {@code true}
     */
    public static void is(boolean check, ErrorBaseInterface e) {
        if (check) {
            throw new BizXWebException(e);
        }
    }

    /**
     * 检查布尔值是否为 {@code true}，如果是，则抛出指定的异常。
     *
     * @param check 要检查的布尔值
     * @param code 异常代码
     * @param message 异常消息
     * @throws BizXWebException 如果布尔值为 {@code true}
     */
    public static void is(boolean check, int code, String message) {
        if (check) {
            throw new BizXWebException(code, message);
        }
    }

    /**
     * 检查布尔值是否为 {@code true}，如果是，则抛出带有指定消息的异常。
     *
     * @param check 要检查的布尔值
     * @param message 异常消息
     * @throws BizXWebException 如果布尔值为 {@code true}
     */
    public static void is(boolean check, String message) {
        if (check) {
            throw new BizXWebException(message);
        }
    }

    /**
     * 使用 {@link Supplier} 检查布尔值是否为 {@code true}，如果是，则抛出指定的异常。
     *
     * @param supplier 提供布尔值的函数
     * @param e 异常接口，提供异常代码和消息
     * @throws BizXWebException 如果供应的布尔值为 {@code true}
     */
    public static void is(Supplier<Boolean> supplier, ErrorBaseInterface e) {
        if (supplier.get()) {
            throw new BizXWebException(e);
        }
    }

    /**
     * 使用 {@link Supplier} 检查布尔值是否为 {@code true}，如果是，则抛出指定的异常。
     *
     * @param supplier 提供布尔值的函数
     * @param code 异常代码
     * @param message 异常消息
     * @throws BizXWebException 如果供应的布尔值为 {@code true}
     */
    public static void is(Supplier<Boolean> supplier, int code, String message) {
        if (supplier.get()) {
            throw new BizXWebException(code, message);
        }
    }

    /**
     * 使用 {@link Supplier} 检查布尔值是否为 {@code true}，如果是，则抛出带有指定消息的异常。
     *
     * @param supplier 提供布尔值的函数
     * @param message 异常消息
     * @throws BizXWebException 如果供应的布尔值为 {@code true}
     */
    public static void is(Supplier<Boolean> supplier, String message) {
        if (supplier.get()) {
            throw new BizXWebException(message);
        }
    }
}
