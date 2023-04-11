package com.biz.web.aop;


import java.lang.annotation.Annotation;

/**
 * @author francis
 * @create: 2023-04-09 22:29
 **/
public interface CheckParameterService {

    /**
     * 处理数据
     *
     * @param annotation 参数
     * @param args       参数值
     * @return
     */
    void handle(Annotation annotation, Object args) throws Throwable;


}
