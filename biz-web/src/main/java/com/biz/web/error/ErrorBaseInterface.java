package com.biz.web.error;

/**
 * 异常接口
 *
 * @author francis
 * @since 1.0.1
 **/
public interface ErrorBaseInterface {

    /**
     * 获取 Code 码
     *
     * @return Code 码
     */
    int getCode();

    /**
     * 获取信息
     *
     * @return 返回信息
     */
    String getMessage();

}
