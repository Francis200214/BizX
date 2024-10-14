package com.biz.security.user.store;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.security.user.UserDetails;
import com.biz.security.user.event.UserAuthenticatedEvent;
import com.biz.security.user.event.UserClearEvent;
import com.biz.security.user.event.UserRefreshedEvent;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * 处理本次请求的用户信息。
 * <p>
 * 此类用于存储、获取、刷新和清除当前请求的用户信息，
 * 并在相应的操作发生时发布认证、注销和刷新事件。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-13
 */
public class SecurityContextHolder implements SmartInitializingSingleton, ApplicationEventPublisherAware {

    /**
     * 存储本次请求的用户信息。
     */
    private SecurityContextStrategy securityContextStrategy;

    /**
     * 事件发布器。
     */
    private ApplicationEventPublisher eventPublisher;

    /**
     * 设置当前用户信息。
     *
     * @param userDetails 用户详细信息
     */
    public void setContext(UserDetails userDetails) {
        securityContextStrategy.setUserDetails(userDetails);
        if (eventPublisher != null) {
            eventPublisher.publishEvent(new UserAuthenticatedEvent(SecurityContextHolder.class, userDetails));
        }
    }

    /**
     * 获取当前用户信息。
     *
     * @return 当前线程中的用户详细信息
     */
    public UserDetails getContext() {
        return securityContextStrategy.getUserDetails();
    }

    /**
     * 刷新当前用户信息。
     *
     * @param userDetails 用户详细信息
     */
    public void refreshContext(UserDetails userDetails) {
        securityContextStrategy.setUserDetails(userDetails);
        if (eventPublisher != null) {
            eventPublisher.publishEvent(new UserRefreshedEvent(SecurityContextHolder.class, userDetails));
        }
    }

    /**
     * 清除当前用户信息。
     */
    public void clearContext() {
        UserDetails userDetails = securityContextStrategy.getUserDetails();
        securityContextStrategy.clear();
        if (eventPublisher != null && userDetails != null) {
            eventPublisher.publishEvent(new UserClearEvent(SecurityContextHolder.class, userDetails));
        }
    }

    /**
     * 在所有单例初始化后，设置 SecurityContextStrategy 的实现。
     */
    @Override
    public void afterSingletonsInstantiated() {
        securityContextStrategy = BizXBeanUtils.getBean(SecurityContextStrategy.class);
    }

    /**
     * 设置应用程序事件发布器。
     *
     * @param applicationEventPublisher 应用程序事件发布器
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        eventPublisher = applicationEventPublisher;
    }

}