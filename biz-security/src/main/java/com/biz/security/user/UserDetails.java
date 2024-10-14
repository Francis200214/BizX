package com.biz.security.user;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * 用户详细信息，包括凭证、角色和其他元数据。
 * <p>
 * 此类用于存储和管理用户信息，以实现安全性和认证目的。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails implements Serializable {

    /**
     * 用户的唯一标识符。
     */
    private String userId;

    /**
     * 用户名。
     */
    private String username;

    /**
     * 用户的密码。
     * <p>
     * 注意：确保密码被安全存储，且不要在日志或序列化形式中暴露。
     * </p>
     */
    private String password;

    /**
     * 分配给用户的角色列表。
     * <p>
     * 角色表示授予用户的不同权限或访问级别。
     * </p>
     */
    private List<String> roles;

    /**
     * 用户可访问的资源列表。
     * <p>
     * 资源定义了用户被允许访问的系统部分或数据。
     * </p>
     */
    private List<String> resources;

    /**
     * 用户凭证的过期时间。
     * <p>
     * 在过期时间之后，用户的会话或认证将被视为过期。
     * </p>
     */
    private Instant expiryTime;

    /**
     * 用户的 IP 地址。
     * <p>
     * 此信息可用于审计目的，或根据用户的位置限制访问。
     * </p>
     */
    private String ipAddress;

    /**
     * 检查用户的凭证是否已过期。
     *
     * @return 如果用户的凭证已过期，则返回 {@code true}；否则返回 {@code false}。
     */
    public boolean isExpired() {
        return expiryTime != null && Instant.now().isAfter(expiryTime);
    }
}
