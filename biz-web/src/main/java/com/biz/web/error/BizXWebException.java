package com.biz.web.error;

import com.biz.common.error.BizXException;
import lombok.Getter;
import lombok.Setter;

/**
 * {@code BizXWebException} 是一个自定义异常类，继承自 {@link BizXException}，
 * 用于在 Web 层处理业务逻辑时抛出特定的异常信息。
 * <p>
 * 该异常类通过扩展 {@link BizXException}，增加了异常代码（{@code code}）和消息（{@code message}）的字段，
 * 以便在发生错误时能够更清晰地传递和处理异常信息。
 *
 * <h3>使用场景:</h3>
 * <p>
 * {@code BizXWebException} 通常在 Web 应用程序中使用，用于处理各种业务异常，如系统错误、自定义错误等。
 * 通过抛出此异常，可以统一管理和处理 Web 层的异常信息。
 *
 * <h3>示例用法:</h3>
 * <pre>{@code
 * if (someConditionFails()) {
 *     throw new BizXWebException(ErrorCode.INVALID_REQUEST);
 * }
 * }</pre>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 * @see BizXException
 * @see ErrorCode
 */
@Setter
@Getter
public class BizXWebException extends BizXException {

    /**
     * 异常代码，用于标识错误类型。
     */
    private int code;

    /**
     * 异常消息，用于描述具体的错误信息。
     */
    private String message;

    /**
     * 默认构造函数，初始化为系统错误（{@link ErrorCode#SYSTEM_ERROR}）。
     */
    public BizXWebException() {
        this.code = ErrorCode.SYSTEM_ERROR.getCode();
        this.message = ErrorCode.SYSTEM_ERROR.getMessage();
    }

    /**
     * 使用指定的错误接口实例初始化异常。
     *
     * @param errorBaseInterface 错误接口实例，提供错误代码和消息
     */
    public BizXWebException(ErrorBaseInterface errorBaseInterface) {
        this.code = errorBaseInterface.getCode();
        this.message = errorBaseInterface.getMessage();
    }

    /**
     * 使用指定的错误消息初始化异常，错误代码默认为系统错误。
     *
     * @param message 错误消息
     */
    public BizXWebException(String message) {
        this.code = ErrorCode.SYSTEM_ERROR.getCode();
        this.message = message;
    }

    /**
     * 使用指定的错误代码和消息初始化异常。
     *
     * @param code    错误代码
     * @param message 错误消息
     */
    public BizXWebException(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
