package com.biz.web.error;

import com.biz.web.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * {@code GlobalExceptionHandler} 是一个全局异常处理器，用于捕获和处理应用程序中抛出的异常。
 * <p>
 * 通过使用 {@link ControllerAdvice} 注解，该类可以在整个 Spring MVC 应用程序中拦截异常，并统一处理它们。
 * 该类提供了两个 {@link ExceptionHandler} 方法，分别用于处理自定义异常 {@link BizXWebException}
 * 和所有未处理的 {@link Throwable} 异常。
 *
 * <h3>主要功能:</h3>
 * <ul>
 *     <li>捕获并处理 {@link BizXWebException}，返回包含错误代码和消息的 {@link ResponseData}。</li>
 *     <li>捕获并处理所有其他未处理的异常，返回通用的系统错误信息。</li>
 *     <li>打印异常堆栈，以便于日志记录和问题排查。</li>
 * </ul>
 *
 * <h3>示例用法:</h3>
 * <pre>{@code
 * // 在某个控制器方法中抛出异常
 * if (user == null) {
 *     throw new BizXWebException(ErrorCode.NOT_LOGIN);
 * }
 * }</pre>
 *
 * <p>此时，{@code GlobalExceptionHandler} 将捕获该异常并返回标准的错误响应。</p>
 *
 * @see ControllerAdvice
 * @see ExceptionHandler
 * @see ResponseData
 * @see BizXWebException
 * @see ErrorCode
 * @see Slf4j
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理 {@link BizXWebException} 异常，返回包含错误代码和消息的响应数据。
     *
     * @param e 捕获的 {@link BizXWebException} 异常
     * @return 包含错误代码和消息的 {@link ResponseData} 对象
     */
    @ExceptionHandler(BizXWebException.class)
    @ResponseBody
    public ResponseData<Object> errorHandle(BizXWebException e) {
        // 打印堆栈异常
        printException(Thread.currentThread(), e);
        return ResponseData.builder()
                .errorCode(e.getCode())
                .errorMessage(e.getMessage())
                .build();
    }

    /**
     * 处理所有未捕获的 {@link Throwable} 异常，返回通用的系统错误响应数据。
     *
     * @param e 捕获的 {@link Throwable} 异常
     * @return 包含通用系统错误代码和消息的 {@link ResponseData} 对象
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseData<Object> errorHandle(Throwable e) {
        // 打印堆栈异常
        printException(Thread.currentThread(), e);
        return ResponseData.builder()
                .errorCode(ErrorCode.SYSTEM_ERROR.getCode())
                .errorMessage(ErrorCode.SYSTEM_ERROR.getMessage())
                .build();
    }

    /**
     * 打印异常堆栈信息到日志，以帮助调试和问题排查。
     *
     * @param t 当前线程
     * @param e 捕获的异常
     */
    private static void printException(Thread t, Throwable e) {
        log.error("", e);
    }
}
