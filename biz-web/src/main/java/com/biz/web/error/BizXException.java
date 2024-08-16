package com.biz.web.error;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 *
 * @author francis
 * @since 1.0.1
 **/
@Setter
@Getter
public class BizXException extends RuntimeException {

    private int code;
    private String message;

    public BizXException() {
        this.code = ErrorCode.SYSTEM_ERROR.getCode();
        this.message = ErrorCode.SYSTEM_ERROR.getMessage();
    }

    public BizXException(ErrorBaseInterface errorBaseInterface) {
        this.code = errorBaseInterface.getCode();
        this.message = errorBaseInterface.getMessage();
    }

    public BizXException(String message) {
        this.code = ErrorCode.SYSTEM_ERROR.getCode();
        this.message = message;
    }

    public BizXException(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
