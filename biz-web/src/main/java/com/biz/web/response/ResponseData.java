package com.biz.web.response;

import lombok.*;

/**
 * 自定义返回值
 *
 * @author francis
 * @create: 2023-05-08 18:22
 **/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {

    private int code;
    private String message;
    private T data;

}
