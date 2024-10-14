package com.biz.security.user.store;

import com.biz.security.user.UserDetails;

import java.time.Instant;

/**
 * 使用 ThreadLocal 本地存储当前用户信息的实现类。
 * <p>
 * 此类实现了 {@link SecurityContextStrategy} 接口，使用 {@link InheritableThreadLocal} 来存储用户信息，
 * 并在超时时间之后自动清除过期的用户信息。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
public class ThreadLocalSecurityContextStrategy implements SecurityContextStrategy {

    /**
     * 存储当前线程的用户信息。
     */
    private static final InheritableThreadLocal<UserDetails> context = new InheritableThreadLocal<>();

    /**
     * 用户信息的过期时间，单位为秒，默认设置为一小时。
     */
    private static final int EXPIRE_SECONDS = 3600;

    /**
     * 设置本次请求的认证用户信息。
     *
     * @param userDetails 用户信息
     */
    @Override
    public void setUserDetails(UserDetails userDetails) {
        userDetails.setExpiryTime(Instant.now().plusSeconds(EXPIRE_SECONDS));
        context.set(userDetails);
    }

    /**
     * 获取本次请求的用户信息。
     *
     * @return {@link UserDetails} 用户信息，如果用户信息已过期则返回 {@code null}
     */
    @Override
    public UserDetails getUserDetails() {
        UserDetails userDetails = context.get();
        if (userDetails != null && userDetails.isExpired()) {
            clear(); // 如果用户信息已过期，清除上下文
            return null;
        }
        return userDetails;
    }

    /**
     * 清除用户信息。
     */
    @Override
    public void clear() {
        context.remove();
    }

}