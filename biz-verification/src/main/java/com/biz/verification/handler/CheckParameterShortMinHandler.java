package com.biz.verification.handler;

import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXCheckShortMin;
import com.biz.verification.error.BizXVerificationException;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * 具体实现检查 Short 最小值的处理器
 * <p>
 * 该类实现了 {@link CheckParameterStrategy} 接口，用于处理带有 {@link BizXCheckShortMin} 注解的参数。
 * </p>
 *
 * @see CheckParameterStrategy
 * @see BizXCheckShortMin
 * @see BizXVerificationException
 * @see Common
 * @see Annotation
 *
 * @author francis
 * @since 1.0.1
 */
@Slf4j
public class CheckParameterShortMinHandler implements CheckParameterStrategy {

    /**
     * 获取当前处理器支持的注解类型
     *
     * @return 支持的注解类型 {@link BizXCheckShortMin}
     */
    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXCheckShortMin.class;
    }

    /**
     * 检查注解和参数是否满足注解的要求
     *
     * @param annotation 注解实例
     * @param value          被检查的参数
     * @param className 类名称
     * @param methodName 方法名称
     * @param fieldName 参数名称
     * @throws BizXVerificationException 如果参数不满足注解的要求，则抛出此异常
     */
    @Override
    public void check(Annotation annotation, Object value, String className, String methodName, String fieldName) throws BizXVerificationException {
        if (value == null) {
            return;
        }

        if (annotation instanceof BizXCheckShortMin) {
            BizXCheckShortMin check = Common.to(annotation);
            if (value instanceof Short) {
                Short num = Common.to(value);
                if (num < check.min()) {
                    throw new BizXVerificationException(check.error().code(), check.error().message(), className, methodName, fieldName);
                }
            }
        }
    }
}
