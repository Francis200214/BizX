package com.biz.web.aop;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author francis
 * @create: 2023-04-09 22:29
 **/
public interface CheckParameterService {

    /**
     * 处理数据
     * 处理GET接口中的参数
     *
     * @param annotation 参数
     * @param args       参数值
     * @return
     */
    void handle(Annotation annotation, Object args) throws Throwable;


    /**
     * 处理对象中的参数数据
     * 处理JSON中的数据
     *
     * @param declaredField
     * @param arg
     */
    void handleField (Field declaredField, Object arg);

}
