package com.biz.web.token;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * token 配置
 *
 * @author francis
 * @create: 2023-04-18 17:52
 **/
@Setter
@Getter
@ToString
@PropertySource(value = "classpath:application.yml")
public class TokenCurrentYml {


    /**
     * rbac 是否开启
     */
    @Value("${biz.token.rbac:false}")
    private boolean rbac;

    /**
     * token 失效时间
     */
    @Value("${biz.token.dieTime:3600000}")
    private long dieTime;

}
