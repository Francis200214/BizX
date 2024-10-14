package com.biz.security.user.event;

import com.biz.security.user.UserDetails;
import org.springframework.context.ApplicationEvent;

/**
 * 用户刷新事件。
 * <p>
 * 当用户信息被刷新时发布此事件，包含刷新后的用户详细信息。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
public class UserRefreshedEvent extends ApplicationEvent {

    /**
     * 新刷新的用户信息。
     */
    private final UserDetails userDetails;

    /**
     * 创建一个用户刷新事件。
     *
     * @param source 事件源
     * @param userDetails 刷新的用户信息
     */
    public UserRefreshedEvent(Object source, UserDetails userDetails) {
        super(source);
        this.userDetails = userDetails;
    }

    /**
     * 获取刷新后的用户信息。
     *
     * @return {@link UserDetails} 刷新的用户信息
     */
    public UserDetails getUserDetails() {
        return userDetails;
    }

}