package com.biz.security.authentication;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.security.authentication.encryption.PasswordEncryptionService;
import com.biz.security.authentication.encryption.PasswordEncryptor;
import com.biz.security.error.AuthenticationException;
import com.biz.security.error.SecurityErrorConstant;
import com.biz.security.user.UserDetails;
import com.biz.security.user.UserDetailsStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * 用户名密码的认证方式实现类。
 *
 * <p>
 * 用于传统的用户名密码登录方式。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-09-20
 */
@Slf4j
public class UsernamePasswordAuthenticationService implements AuthenticationService, SmartInitializingSingleton {

    /**
     * 密码加密服务。
     */
    private PasswordEncryptionService passwordEncryptionService;

    /**
     * 获取用户信息策略。
     */
    private UserDetailsStrategy userDetailsStrategy;

    /**
     * 用户名密码认证，验证用户输入的用户名和密码是否正确。
     *
     * @param loginRequest 包含用户名和密码的登录请求
     * @return {@link UserDetails} 返回认证成功后的用户信息
     * @throws AuthenticationException 当认证失败时抛出异常
     */
    @Override
    public UserDetails authenticate(LoginRequest loginRequest) throws AuthenticationException {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (username == null || password == null) {
            throw new AuthenticationException(SecurityErrorConstant.USERNAME_PASSWORD_INFORMATION_IS_MISSING_FAILED);
        }

        // 从数据库或存储中获取用户信息
        UserDetails userDetails = findUserDetailsByUsername(username);
        // 输入的密码与数据库存储的加密密码进行匹配
        if (passwordEncryptionService.matches(password, userDetails.getPassword())) {
            // 返回用户详细信息
            return userDetails;

        } else {
            throw new AuthenticationException(SecurityErrorConstant.USER_PASSWORD_ERROR);
        }
    }

    /**
     * 根据用户名获取用户存储在数据库中的加密密码。
     *
     * @param username 用户名
     * @return {@link UserDetails} 用户信息
     * @throws RuntimeException 当获取用户信息失败时抛出异常
     */
    private UserDetails findUserDetailsByUsername(String username) throws RuntimeException {
        UserDetails userDetails = userDetailsStrategy.loadUserByUsername(username);
        if (userDetails == null) {
            throw new RuntimeException("UserDetails is null");
        }
        return userDetails;
    }

    /**
     * 在所有单例初始化后，设置必要的依赖。
     */
    @Override
    public void afterSingletonsInstantiated() {
        this.passwordEncryptionService = new PasswordEncryptionService(BizXBeanUtils.getBean(PasswordEncryptor.class));
        try {
            this.userDetailsStrategy = BizXBeanUtils.getBean(UserDetailsStrategy.class);
        } catch (Exception e) {
            throw new RuntimeException("UserDetailsStrategy is null");
        }
    }
}
