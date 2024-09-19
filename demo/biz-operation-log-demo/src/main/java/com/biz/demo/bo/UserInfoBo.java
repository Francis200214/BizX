package com.biz.demo.bo;

import lombok.*;

/**
 * 用户信息
 *
 * @author francis
 * @create 2024-09-19
 **/
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoBo {

    private String name;

    private int age;

    private String school;

}
