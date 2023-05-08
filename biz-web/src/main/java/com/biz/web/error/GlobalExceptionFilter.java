package com.biz.web.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常
 *
 * @author francis
 * @create: 2023-05-08 18:08
 **/
@ControllerAdvice
public class GlobalExceptionFilter {

    @ExceptionHandler(Throwable.class)
    public String errorHandle(Throwable e){
        return e.getMessage();
    }


}
