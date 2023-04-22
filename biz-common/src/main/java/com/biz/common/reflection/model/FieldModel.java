package com.biz.common.reflection.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 通过反射获得Class的属性信息实体
 *
 * @author francis
 * @create 2023/4/2 10:04
 */
@Setter
@Getter
@Builder
@EqualsAndHashCode
public class FieldModel {

    /**
     * 属性名
     */
    private String name;

    /**
     * 修饰符
     */
    private String modifier;

    /**
     * 数据类型
     */
    private String typeName;

    /**
     * 属性上的注解
     */
    private Annotation[] annotations;

    /**
     * 原始值
     */
    private Field field;

}
