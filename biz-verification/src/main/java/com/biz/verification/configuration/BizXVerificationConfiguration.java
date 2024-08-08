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

    /**
     * 创建并配置 {@link CheckParameterCollectionIsEmptyHandler} 校验处理器。
     *
     * @return {@link CheckParameterCollectionIsEmptyHandler} 实例
     */
    @Bean
    public CheckParameterCollectionIsEmptyHandler checkParameterCollectionIsEmptyHandler() {
        return new CheckParameterCollectionIsEmptyHandler();
    }

    /**
     * 创建并配置 {@link CheckParameterDateTimeHandler} 校验处理器。
     *
     * @return {@link CheckParameterDateTimeHandler} 实例
     */
    @Bean
    public CheckParameterDateTimeHandler checkParameterDateTimeHandler() {
        return new CheckParameterDateTimeHandler();
    }


    /**
     * 创建并配置 {@link CheckParameterDoubleMaxHandler} 校验处理器。
     *
     * @return {@link CheckParameterDoubleMaxHandler} 实例
     */
    @Bean
    public CheckParameterDoubleMaxHandler checkParameterDoubleMaxHandler() {
        return new CheckParameterDoubleMaxHandler();
    }

    /**
     * 创建并配置 {@link CheckParameterDoubleMinHandler} 校验处理器。
     *
     * @return {@link CheckParameterDoubleMinHandler} 实例
     */
    @Bean
    public CheckParameterDoubleMinHandler checkParameterDoubleMinHandler() {
        return new CheckParameterDoubleMinHandler();
    }

    /**
     * 创建并配置 {@link CheckParameterFloatMaxHandler} 校验处理器。
     *
     * @return {@link CheckParameterFloatMaxHandler} 实例
     */
    @Bean
    public CheckParameterFloatMaxHandler checkParameterFloatMaxHandler() {
        return new CheckParameterFloatMaxHandler();
    }

    /**
     * 创建并配置 {@link CheckParameterFloatMinHandler} 校验处理器。
     *
     * @return {@link CheckParameterFloatMinHandler} 实例
     */
    @Bean
    public CheckParameterFloatMinHandler checkParameterFloatMinHandler() {
        return new CheckParameterFloatMinHandler();
    }

    /**
     * 创建并配置 {@link CheckParameterIntegerMaxHandler} 校验处理器。
     *
     * @return {@link CheckParameterIntegerMaxHandler} 实例
     */
    @Bean
    public CheckParameterIntegerMaxHandler checkParameterIntegerMaxHandler() {
        return new CheckParameterIntegerMaxHandler();
    }

    /**
     * 创建并配置 {@link CheckParameterIntegerMinHandler} 校验处理器。
     *
     * @return {@link CheckParameterIntegerMinHandler} 实例
     */
    @Bean
    public CheckParameterIntegerMinHandler checkParameterIntegerMinHandler() {
        return new CheckParameterIntegerMinHandler();
    }

    /**
     * 创建并配置 {@link CheckParameterIsNullHandler} 校验处理器。
     *
     * @return {@link CheckParameterIsNullHandler} 实例
     */
    @Bean
    public CheckParameterIsNullHandler checkParameterIsNullHandler() {
        return new CheckParameterIsNullHandler();
    }

    /**
     * 创建并配置 {@link CheckParameterLongMaxHandler} 校验处理器。
     *
     * @return {@link CheckParameterLongMaxHandler} 实例
     */
    @Bean
    public CheckParameterLongMaxHandler checkParameterLongMaxHandler() {
        return new CheckParameterLongMaxHandler();
    }

    /**
     * 创建并配置 {@link CheckParameterLongMinHandler} 校验处理器。
     *
     * @return {@link CheckParameterLongMinHandler} 实例
     */
    @Bean
    public CheckParameterLongMinHandler checkParameterLongMinHandler() {
        return new CheckParameterLongMinHandler();
    }

    /**
     * 创建并配置 {@link CheckParameterShortMaxHandler} 校验处理器。
     *
     * @return {@link CheckParameterShortMaxHandler} 实例
     */
    @Bean
    public CheckParameterShortMaxHandler checkParameterShortMaxHandler() {
        return new CheckParameterShortMaxHandler();
    }

    /**
     * 创建并配置 {@link CheckParameterShortMinHandler} 校验处理器。
     *
     * @return {@link CheckParameterShortMinHandler} 实例
     */
    @Bean
    public CheckParameterShortMinHandler checkParameterShortMinHandler() {
        return new CheckParameterShortMinHandler();
    }

    /**
     * 创建并配置 {@link CheckParameterSizeHandler} 校验处理器。
     *
     * @return {@link CheckParameterSizeHandler} 实例
     */
    @Bean
    public CheckParameterSizeHandler checkParameterSizeHandler() {
        return new CheckParameterSizeHandler();
    }

}
