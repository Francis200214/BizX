package com.biz.common.error;

import java.io.Serializable;

/**
 * 异常
 *
 * @author francis
 * @create 2024-09-20
 * @since 1.0.1
 **/
public interface BizXErrorConstant extends Serializable {

    /**
     * 获取异常 Code
     */
    int getCode();

    /**
     * 获取异常信息
     */
    String getMessage();

}
