package com.demo.service.bo;

import lombok.*;

/**
 * 添加用户信息
 *
 * @author francis
 * @since 2024-06-03 13:55
 **/
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserBo {

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户年龄
     */
    private int age;

    /**
     * 用户学校
     */
    private String school;

}
