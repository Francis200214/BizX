package com.biz.web.error;

import com.biz.web.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BizXException.class)
    @ResponseBody
    public ResponseData<Object> errorHandle(BizXException e){
        // 打印堆栈异常
        printException(Thread.currentThread(), e);
        return ResponseData.builder()
                .errorCode(e.getCode())
                .errorMessage(e.getMessage())
                .build();
    }


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseData<Object> errorHandle(Throwable e){
        // 打印堆栈异常
        printException(Thread.currentThread(), e);
        return ResponseData.builder()
                .errorCode(ErrorCode.SYSTEM_ERROR.getCode())
                .errorMessage(ErrorCode.SYSTEM_ERROR.getMessage())
                .build();
    }



    private static void printException(Thread t, Throwable e) {
//        StackTraceElement[] ses = e.getStackTrace();
        log.error("", e);
//        System.err.println("Exception in thread \"" + t.getName() + "\" " + e);
//        for (StackTraceElement se : ses) {
//            System.err.println("\tat " + se);
//        }
//        Throwable ec = e.getCause();
//        if (ec != null) {
//            printException(t, ec);
//        }
    }



}
