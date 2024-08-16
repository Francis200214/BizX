package com.biz.web.response;

import lombok.*;

import java.io.Serializable;

/**
 * 自定义返回值
 *
 * @author francis
 * @since 1.0.1
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
