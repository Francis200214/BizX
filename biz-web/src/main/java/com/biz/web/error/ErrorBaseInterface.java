package com.biz.web.error;

/**
 * {@code ErrorBaseInterface} 是一个用于定义异常信息的接口。
 * <p>
 * 该接口提供了获取错误代码和错误消息的方法，任何实现此接口的类都需要提供这些基本的异常信息。
 * 它通常用于统一管理和表示系统中的各种错误类型，以便在异常处理时能够根据错误代码和消息做出相应的处理。
 *
 * <h3>典型使用场景:</h3>
 * <p>
 * {@code ErrorBaseInterface} 常用于定义应用程序中的错误枚举或自定义异常类，通过实现该接口，
 * 开发者可以规范化错误信息的获取方式，并确保错误处理的统一性。
 *
 * <h3>示例用法:</h3>
 * <pre>{@code
 * public enum ErrorCode implements ErrorBaseInterface {
 *     INVALID_REQUEST(400, "Invalid request"),
 *     SYSTEM_ERROR(500, "System error");
 *
 *     private final int code;
 *     private final String message;
 *
 *     ErrorCode(int code, String message) {
 *         this.code = code;
 *         this.message = message;
 *     }
 *
 *     @Override
 *     public int getCode() {
 *         return code;
 *     }
 *
 *     @Override
 *     public String getMessage() {
 *         return message;
 *     }
 * }
 * }</pre>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
public interface ErrorBaseInterface {

    /**
     * 获取错误代码。
     *
     * @return 错误代码
     */
    int getCode();

    /**
     * 获取错误消息。
     *
     * @return 错误消息
     */
    String getMessage();

}
