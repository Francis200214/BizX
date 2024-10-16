package com.biz.security.error;

import com.biz.common.error.BizXException;

/**
 * 无用户信息异常。
 *
 * <p>
 * 用于表示在获取用户详细信息时发生的错误。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
public class NoneUserDetailsException extends BizXException {

    /**
     * 异常 Code 码。
     */
    private final int CODE;

    /**
     * 异常信息。
     */
    private final String MESSAGE;

    /**
     * 构造方法，根据错误常量创建异常实例。
     *
     * @param errorConstant 错误常量
     */
    public NoneUserDetailsException(SecurityErrorConstant errorConstant) {
        this.CODE = errorConstant.getCode();
        this.MESSAGE = errorConstant.getMessage();
    }

    /**
     * 获取异常 Code。
     *
     * @return 异常 Code
     */
    @Override
    public int getCode() {
        return this.CODE;
    }

    /**
     * 获取异常信息。
     *
     * @return 异常信息
     */
    @Override
    public String getMessage() {
        return this.MESSAGE;
    }
}
