package com.biz.common.reflection.model;

import lombok.*;

/**
 * 方法参数实体模型
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
public class ParameterTypeModel {

    /**
     * 参数名
     */
    private String name;

}
