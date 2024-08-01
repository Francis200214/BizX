package com.biz.web.response;

import lombok.*;

import java.io.Serializable;

/**
 * 自定义返回值
 *
 * @author francis
 * @since 2023-05-08 18:22
 **/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> implements Serializable {

    private int errorCode;
    private String errorMessage;
    private T data;

}
