package com.biz.common.reflection.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 方法参数实体模型
 *
 * @author francis
 * @create 2023/4/2 10:15
 */
@Setter
@Getter
@Builder
@EqualsAndHashCode
public class ParameterTypeModel {

    /**
     * 参数名
     */
    private String name;

}
