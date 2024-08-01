package com.biz.web.aop.handler;

import java.lang.annotation.Annotation;

/**
 * 入参检查
 *
 * @author francis
 * @since 2023-04-08 16:25
 **/
public interface CheckParameterStrategy {

    /**
     * 获取检查类型
     *
     * @return
     */
    Class<?> getCheckAnnotation();

    /**
     * 检查入参是否符合规则
     *
     * @param annotation
     * @param o          入参数据
     */
    void check(Annotation annotation, Object o) throws Exception;

}
