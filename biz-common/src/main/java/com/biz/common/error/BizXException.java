package com.biz.common.error;

/**
 * BizX 最高异常类接口。
 * <p>定义了获取错误码和错误信息的方法。</p>
 *
 * @author francis
 * @since 2024-08-08
 */
public abstract class BizXException extends Exception {

    /**
     * 获取错误码。
     *
     * @return 错误码
     */
    public abstract int getCode();

    /**
     * 获取错误信息。
     *
     * @return 错误信息
     */
    public abstract String getMessage();

}
