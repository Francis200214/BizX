package com.biz.web.aop.handler;

import java.lang.reflect.Parameter;

/**
 * 入参检查
 *
 * @author francis
 * @create: 2023-04-08 16:25
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
     * @param parameter Parameter实体
     * @param o 入参数据
     */
    void check(Parameter parameter, Object o) throws Exception;

}
