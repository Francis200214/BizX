package com.biz.common.reflection.model;

import lombok.*;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 通过反射获得Class的属性信息实体，表示类中的一个字段及其相关信息。
 * 该模型包含字段的名称、修饰符、数据类型、注解及其对应的原始Field对象。
 *
 * <p>使用该模型可以更方便地处理类的字段信息，尤其是在需要动态分析类结构或操作字段时。</p>
 *
 * <p>例如，通过该模型可以获取字段的名称、修饰符、类型名称以及该字段上的注解数组。</p>
 *
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 * @see java.lang.reflect.Field
 * @see java.lang.annotation.Annotation
 */
@Setter
@Getter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FieldModel implements Serializable {

    /**
     * 字段的名称。
     */
    private String name;

    /**
     * 字段的修饰符，例如：public、private、protected等。
     */
    private String modifier;

    /**
     * 字段的数据类型名称，例如：java.lang.String、int等。
     */
    private String typeName;

    /**
     * 字段上声明的注解数组。
     */
    private Annotation[] annotations;

    /**
     * 字段对应的原始Field对象，表示该字段的实际反射信息。
     */
    private Field field;

}
