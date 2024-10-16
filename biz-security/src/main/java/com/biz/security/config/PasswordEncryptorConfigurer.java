package com.biz.security.config;

import com.biz.security.authentication.encryption.MD5PasswordEncryptor;
import com.biz.security.authentication.encryption.PasswordEncryptor;
import com.biz.security.authentication.encryption.SHA256PasswordEncryptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 密码加密配置。
 *
 * <p>
 * 配置用于 Biz-Security 组件的密码加密服务。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
@ConditionalOnProperty(prefix = "biz.security", name = "enabled", havingValue = "true")
public class PasswordEncryptorConfigurer {

    /**
     * 注册 MD5 加密实现类。
     * 如果配置文件中指定了使用 md5 作为加密方式，则优先使用 MD5 实现。
     *
     * @return MD5PasswordEncryptor 实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "biz.security.password", name = "encryptor", havingValue = "md5", matchIfMissing = true)
    @Primary
    public PasswordEncryptor md5PasswordEncryptor() {
        return new MD5PasswordEncryptor();
    }

    /**
     * 注册 SHA-256 加密实现类。
     * 如果配置文件中指定了使用 sha-256 作为加密方式，则优先使用 SHA-256 实现。
     *
     * @return SHA256PasswordEncryptor 实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "biz.security.password", name = "encryptor", havingValue = "sha-256")
    public PasswordEncryptor sha256PasswordEncryptor() {
        return new SHA256PasswordEncryptor();
    }

    /**
     * 注册默认的密码加密实现类。
     * 如果开发者实现了自定义 PasswordEncryptor 接口，且未指定具体的加密方式，则优先使用开发者的实现类。
     * 如果没有其他 PasswordEncryptor 实例时才使用 MD5 加密实现类。
     *
     * @return PasswordEncryptor 实例
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncryptor.class)
    public PasswordEncryptor defaultPasswordEncryptor() {
        return new MD5PasswordEncryptor();
    }
}
