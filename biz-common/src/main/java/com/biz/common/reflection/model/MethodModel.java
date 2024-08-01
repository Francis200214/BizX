package com.biz.common.reflection.model;

import lombok.*;

import java.util.Set;

/**
 * 方法实体模型
 *
 * @author francis
 * @since 2023/4/2 10:25
 */
@Setter
@Getter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MethodModel {

    /**
     * 修饰符
     */
    private String modifier;

    /**
     * 名称
     */
    private String name;

    /**
     * 返回值类型
     */
    private String returnType;

    /**
     * 方法参数
     * 没有参数为空Set
     */
    private Set<ParameterTypeModel> parameterTypeModels;

}
