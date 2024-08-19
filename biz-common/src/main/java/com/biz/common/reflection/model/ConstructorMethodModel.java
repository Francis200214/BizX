package com.biz.common.reflection.model;

import lombok.*;

import java.util.Set;

/**
 * 构造器实体模型
 *
 * @author francis
 * @since 1.0.1
 */
@Setter
@Getter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ConstructorMethodModel {

    /**
     * 修饰符
     */
    private String modifier;

    /**
     * 名称
     */
    private String name;

    /**
     * 构造器参数
     * 无参构造该参数为空Set
     */
    private Set<ParameterTypeModel> parameterTypeModels;

}
