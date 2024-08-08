package com.biz.verification.error;

import com.biz.common.error.BizXException;
import com.biz.common.utils.ErrorCodeConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * 参数数据校验的异常类。
 * <p>所有的数据校验检查处理器在处理数据过程中，如果出现了未按照定义的规则数据，就会抛出 {@link BizXVerificationException}。</p>
 *
 * <pre>
 * 示例实现：
 * {@code
 * throw new BizXVerificationException(1000, "This is exception information.");
 * }
 * </pre>
 *
 * @author francis
 * @version 1.0.0
 * @see BizXException
 * @since 2024-08-08
 **/
@Slf4j
public class BizXVerificationException extends BizXException {

    /**
     * 错误码。
     */
    private final int code;

    /**
     * 错误信息。
     */
    private final String message;

    /**
     * 构造参数校验异常。
     * <p>使用默认异常Code码和信息。</p>
     */
    public BizXVerificationException() {
        this.code = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        this.message = ErrorCodeConstant.DEFAULT_ERROR.MESSAGE;
    }

    /**
     * 构造参数校验异常。
     *
     * @param code    错误码
     * @param message 错误信息
     */
    public BizXVerificationException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误码。
     *
     * @return 错误码
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * 获取错误信息。
     *
     * @return 错误信息
     */
    @Override
    public String getMessage() {
        return message;
    }


}
