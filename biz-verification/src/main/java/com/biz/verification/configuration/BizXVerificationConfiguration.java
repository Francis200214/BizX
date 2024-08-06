package com.biz.verification.configuration;

import com.biz.verification.AbstractBizXCheckParameter;
import com.biz.verification.condition.CheckScanPackageCondition;
import com.biz.verification.factory.CheckParameterFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BizX 校验入参配置类。
 * <p>根据配置文件中的属性自动配置 {@link AbstractBizXCheckParameter} Bean。</p>
 *
 * <p>该配置类在配置文件中存在 <code>biz.verification.enabled=true</code> 属性时才会被加载。</p>
 *
 * <pre>
 * 示例配置：
 * biz.verification.enabled=true
 * </pre>
 *
 * @see AbstractBizXCheckParameter
 * @see CheckParameterFactory
 * @see CheckScanPackageCondition
 *
 * @version 1.0
 * @since 2024-08-06
 * @author francis
 */
@ConditionalOnProperty(prefix = "biz.verification", name = "enabled", havingValue = "true")
@Configuration
public class BizXVerificationConfiguration {

    /**
     * 创建并配置 {@link AbstractBizXCheckParameter} Bean。
     *
     * @return {@link AbstractBizXCheckParameter} 实例
     */
    @Bean
    public AbstractBizXCheckParameter bizXCheckParameter() {
        return new AbstractBizXCheckParameter();
    }

    /**
     * 创建并配置 {@link CheckParameterFactory} 校验参数工厂 Bean。
     *
     * @return {@link CheckParameterFactory} 实例
     */
    @Bean
    public CheckParameterFactory checkParameterFactory() {
        return new CheckParameterFactory();
    }

    /**
     * 创建并配置 {@link CheckScanPackageCondition} 校验扫描包条件 Bean。
     *
     * @return {@link CheckScanPackageCondition} 实例
     */
    @Bean
    public CheckScanPackageCondition checkScanPackageCondition() {
        return new CheckScanPackageCondition();
    }

}
