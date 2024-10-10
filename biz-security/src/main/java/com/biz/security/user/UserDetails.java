package com.biz.security.user;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * 用户信息
 *
 * @author francis
 * @create 2024-09-20
 * @since 1.0.1
 **/
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色列表
     */
    private List<String> roles;

    /**
     * 资源列表
     */
    private List<String> resources;

    /**
     * 过期时间
     */
    private Instant expiryTime;

    /**
     * 用户IP地址
     */
    private String ipAddress;

    /**
     * 是否过期
     *
     * @return true:过期 false:未过期
     */
    public boolean isExpired() {
        return expiryTime != null && Instant.now().isAfter(expiryTime);
    }

}
