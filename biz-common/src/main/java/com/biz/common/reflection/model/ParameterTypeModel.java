package com.biz.common.reflection.model;

import lombok.*;

import java.io.Serializable;

/**
 * 方法参数实体模型，用于表示方法的参数信息。
 * 该模型包含参数的名称，可以用于反射操作中对方法参数的描述和处理。
 *
 * <p>使用该模型可以更方便地处理类的方法参数，尤其是在需要动态生成或分析方法参数时。</p>
 *
 * <p>例如，通过该模型可以获取方法参数的名称，并结合其他模型进行更复杂的反射操作。</p>
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
@Setter
@Getter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ParameterTypeModel implements Serializable {

    /**
     * 方法参数的名称。
     */
    private String name;

}
