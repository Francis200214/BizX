package com.biz.verification.strategy;

import com.biz.verification.error.BizXVerificationException;

import java.lang.annotation.Annotation;

/**
 * 入参检查策略接口。
 * <p>该接口定义了检查方法参数是否符合规则的策略。</p>
 * <p>该接口是一个校验的扩展点，用户可以自定义增加校验注解和实现类，只需要将扩展的实现类注册在 Spring Bean 容器内即可。</p>
 *
 * <pre>
 * 扩展示例实现：
 * {@code
 * @Component
 * public class CheckXXParameterStrategy implements CheckParameterStrategy {
 *     @Override
 *     public Class<? extends Annotation> getCheckAnnotation() {
 *         // 扩展自定义参数校验注解
 *         return CheckXXAnnotation.class;
 *     }
 *
 *     @Override
 *     public void check(Annotation annotation, Object o) throws BizXVerificationException {
 *         // 实现自定义参数校验注解的检查逻辑
 *     }
 * }
 * }
 * </pre>
 *
 * @see Annotation
 * @see BizXVerificationException
 * @author francis
 * @since 2023-04-08
 * @version 1.0.0
 **/
public interface CheckParameterStrategy {

    /**
     * 获取检查类型。
     *
     * @return 需要检查的注解类型
     */
    Class<? extends Annotation> getCheckAnnotation();

    /**
     * 检查入参是否符合规则。
     *
     * @param annotation 需要检查的注解实例
     * @param o          入参数据
     * @throws BizXVerificationException 如果检查失败则抛出异常
     */
    void check(Annotation annotation, Object o) throws BizXVerificationException;
}
