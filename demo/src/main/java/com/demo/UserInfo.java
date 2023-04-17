package com.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author francis
 * @create: 2023-04-14 16:13
 **/
@Component
@ToString
@Setter
@Getter
@PropertySource("classpath:application.yml")
public class UserInfo {

    @Value("${user.name:张三}")
    private String name;

    @Value("${user.age}")
    private int age;

}
