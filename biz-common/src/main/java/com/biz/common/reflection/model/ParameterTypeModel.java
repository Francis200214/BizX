package com.biz.common.reflection.model;

import lombok.*;

/**
 * 方法参数实体模型
 *
 * @author francis
 * @create 2023/4/2 10:15
 */
@Setter
@Getter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ParameterTypeModel {

    /**
     * 参数名
     */
    private String name;

}
