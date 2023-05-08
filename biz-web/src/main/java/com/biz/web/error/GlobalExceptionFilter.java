package com.biz.web.error;

import com.biz.web.response.ResponseData;
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

    @ExceptionHandler(BusinessException.class)
    public ResponseData<Object> errorHandle(BusinessException e){
        return ResponseData.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
    }


    @ExceptionHandler(Throwable.class)
    public ResponseData<Object> errorHandle(Throwable e){
        return ResponseData.builder()
                .code(ErrorCode.SYSTEM_ERROR.getCode())
                .message(ErrorCode.SYSTEM_ERROR.getMessage())
                .build();
    }


}
