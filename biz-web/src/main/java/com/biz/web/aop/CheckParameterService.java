package com.biz.web.aop;


import java.lang.reflect.Parameter;

/**
 * @author francis
 * @create: 2023-04-09 22:29
 **/
public interface CheckParameterService {

    /**
     * 处理数据
     *
     * @param parameter 参数
     * @param args 参数值
     * @return
     */
    void handle(Parameter parameter, Object args) throws Throwable;


}
