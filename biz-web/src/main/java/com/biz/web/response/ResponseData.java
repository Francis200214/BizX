package com.biz.web.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义返回值
 *
 * @author francis
 * @create: 2023-05-08 18:22
 **/
@Setter
@Getter
public class ResponseData<T> {

    private int code;
    private String message;
    private T data;

    private ResponseData() {
    }

}
