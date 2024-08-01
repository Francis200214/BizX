package com.biz.web.aop;


import java.lang.annotation.Annotation;

/**
 * @author francis
 * @since 2023-04-09 22:29
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


}
