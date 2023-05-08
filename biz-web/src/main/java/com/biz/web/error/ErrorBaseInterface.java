package com.biz.web.error;

/**
 * 异常接口
 *
 * @author francis
 * @create: 2023-05-08 18:10
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
