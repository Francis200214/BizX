package com.biz.web.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 请求对象封装
 *
 * @author francis
 * @create: 2023-07-06 11:26
 **/
@Setter
@Getter
@ToString
public class PageRequest<T> {

    /**
     * 第几页
     */
    private Long num;

    /**
     * 每页多少条
     */
    private Integer pageSize;

    /**
     * 请求参数
     */
    private T data;

}
