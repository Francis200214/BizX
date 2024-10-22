package com.biz.security.error;

import com.biz.common.error.BizXException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 认证类型转换异常
 *
 * @author francis
 * @create 2024-10-18
 **/
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationTypeConvertException extends BizXException {


    /**
     * 异常 Code 码。
     */
    private int CODE = SecurityErrorConstant.AUTHENTICATION_FAILED.getCode();

    /**
     * 异常信息。
     */
    private String MESSAGE = SecurityErrorConstant.AUTHENTICATION_FAILED.getMessage();

    /**
     * 根据错误常量创建认证异常实例。
     *
     * @param errorConstant 错误常量
     */
    public AuthenticationTypeConvertException(SecurityErrorConstant errorConstant) {
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
