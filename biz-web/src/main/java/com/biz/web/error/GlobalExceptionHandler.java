package com.biz.web.error;

import com.biz.web.response.ResponseData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author francis
 * @create: 2023-05-08 18:08
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizXException.class)
    @ResponseBody
    public ResponseData<Object> errorHandle(BizXException e){
        return ResponseData.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
    }


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseData<Object> errorHandle(Throwable e){
        return ResponseData.builder()
                .code(ErrorCode.SYSTEM_ERROR.getCode())
                .message(ErrorCode.SYSTEM_ERROR.getMessage())
                .build();
    }


}
