package com.biz.web.error;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 *
 * @author francis
 * @create: 2023-05-08 18:13
 **/
@Setter
@Getter
public class BusinessException extends RuntimeException {

    private int code;
    private String message;

    public BusinessException(ErrorBaseInterface errorBaseInterface) {
        this.code = errorBaseInterface.getCode();
        this.message = errorBaseInterface.getMessage();
    }

}
