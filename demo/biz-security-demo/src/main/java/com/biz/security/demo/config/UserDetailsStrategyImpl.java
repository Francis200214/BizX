package com.biz.security.demo.config;

import com.biz.common.random.RandomUtils;
import com.biz.security.user.UserDetails;
import com.biz.security.user.UserDetailsStrategy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 获取用户信息接口
 *
 * @author francis
 * @create 2024-10-11
 * @since 1.0.1
 **/
@Component
public class UserDetailsStrategyImpl implements UserDetailsStrategy {

    private static final List<String> roles = Arrays.asList("USER", "ADMIN");

    private static final List<String> resources = Arrays.asList("AddUser", "DeleteUser");

    @Override
    public UserDetails loadUserByUsername(String username) {
        return UserDetails.builder()
                .userId(RandomUtils.generateStr())
                .username(RandomUtils.generateStr())
                .password("86C94D4D2CCA2D2DD6279DBD02B932B2")
                .roles(roles)
                .resources(resources)
                .build();
    }

    @Override
    public UserDetails loadUserByToken(String token) {
        return UserDetails.builder()
                .userId(RandomUtils.generateStr())
                .username(RandomUtils.generateStr())
                .roles(roles)
                .resources(resources)
                .build();
    }

    @Override
    public UserDetails loadUserByOAuth2(String provider, String oAuth2Info) {
        return UserDetails.builder()
                .userId(RandomUtils.generateStr())
                .username(RandomUtils.generateStr())
                .roles(roles)
                .resources(resources)
                .build();
    }
}
