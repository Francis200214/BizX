package com.biz.common.reflection.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 构造器实体模型
 *
 * @author francis
 * @create 2023/4/2 10:15
 */
@Setter
@Getter
@Builder
@EqualsAndHashCode
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
