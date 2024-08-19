package com.biz.common.reflection.model;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * 构造器实体模型，用于表示一个类的构造器方法的相关信息。
 * 该模型包含构造器的修饰符、名称和参数类型列表。
 *
 * <p>使用该模型可以更方便地处理类的反射操作，尤其是在需要动态生成或分析构造方法时。</p>
 *
 * <p>例如，通过该模型可以获取构造器的修饰符、名称，以及构造器所接受的参数类型集合。</p>
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
public class ConstructorMethodModel implements Serializable {

    /**
     * 构造器的修饰符，例如：public、private、protected等。
     */
    private String modifier;

    /**
     * 构造器的名称，一般情况下应与类名一致。
     */
    private String name;

    /**
     * 构造器参数的类型集合。
     * 如果构造器没有参数，则该集合应为空。
     */
    private Set<ParameterTypeModel> parameterTypeModels;

}
