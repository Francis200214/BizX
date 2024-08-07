package com.biz.verification.configuration;

import com.biz.verification.AbstractBizXCheckParameter;
import com.biz.verification.condition.CheckScanPackageCondition;
import com.biz.verification.factory.CheckParameterFactory;
import com.biz.verification.handler.*;
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
 * @author francis
 * @version 1.0
 * @see AbstractBizXCheckParameter
 * @see CheckParameterFactory
 * @see CheckScanPackageCondition
 * @since 2024-08-06
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


    @Bean
    public CheckParameterCollectionIsEmptyHandler checkParameterCollectionIsEmptyHandler() {
        return new CheckParameterCollectionIsEmptyHandler();
    }

    @Bean
    public CheckParameterDoubleMaxHandler checkParameterDoubleMaxHandler() {
        return new CheckParameterDoubleMaxHandler();
    }


    @Bean
    public CheckParameterDoubleMinHandler checkParameterDoubleMinHandler() {
        return new CheckParameterDoubleMinHandler();
    }


    @Bean
    public CheckParameterFloatMaxHandler checkParameterFloatMaxHandler() {
        return new CheckParameterFloatMaxHandler();
    }


    @Bean
    public CheckParameterFloatMinHandler checkParameterFloatMinHandler() {
        return new CheckParameterFloatMinHandler();
    }


    @Bean
    public CheckParameterIntegerMaxHandler checkParameterIntegerMaxHandler() {
        return new CheckParameterIntegerMaxHandler();
    }

    @Bean
    public CheckParameterIntegerMinHandler checkParameterIntegerMinHandler() {
        return new CheckParameterIntegerMinHandler();
    }

    @Bean
    public CheckParameterIsNullHandler checkParameterIsNullHandler() {
        return new CheckParameterIsNullHandler();
    }


    @Bean
    public CheckParameterLongMaxHandler checkParameterLongMaxHandler() {
        return new CheckParameterLongMaxHandler();
    }


    @Bean
    public CheckParameterLongMinHandler checkParameterLongMinHandler() {
        return new CheckParameterLongMinHandler();
    }


    @Bean
    public CheckParameterShortMaxHandler checkParameterShortMaxHandler() {
        return new CheckParameterShortMaxHandler();
    }


    @Bean
    public CheckParameterShortMinHandler checkParameterShortMinHandler() {
        return new CheckParameterShortMinHandler();
    }

    @Bean
    public CheckParameterSizeHandler checkParameterSizeHandler() {
        return new CheckParameterSizeHandler();
    }

}
