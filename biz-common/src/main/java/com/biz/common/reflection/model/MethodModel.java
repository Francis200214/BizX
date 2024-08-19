package com.biz.common.reflection.model;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * 方法实体模型，用于表示类中的方法及其相关信息。
 * 该模型包含方法的修饰符、名称、返回值类型和参数类型列表。
 *
 * <p>使用该模型可以更方便地处理类的方法信息，尤其是在需要动态分析或操作方法时。</p>
 *
 * <p>例如，通过该模型可以获取方法的修饰符、名称、返回值类型，以及方法的参数类型集合。</p>
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 * @see ParameterTypeModel
 */
@Setter
@Getter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MethodModel implements Serializable {

    /**
     * 方法的修饰符，例如：public、private、protected等。
     */
    private String modifier;

    /**
     * 方法的名称。
     */
    private String name;

    /**
     * 方法的返回值类型名称，例如：java.lang.String、int等。
     */
    private String returnType;

    /**
     * 方法的参数类型集合。
     * 如果方法没有参数，则该集合应为空。
     */
    private Set<ParameterTypeModel> parameterTypeModels;

}
