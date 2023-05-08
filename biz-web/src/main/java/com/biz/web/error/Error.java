package com.biz.web.error;

/**
 * @author francis
 * @create: 2023-05-08 18:10
 **/
public interface Error {

    void throwError(int code, String message);

}
