package com.biz.verification.handler;

import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXCheckLongMax;
import com.biz.verification.error.BizXVerificationException;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * 具体实现检查 Long 最大值的处理器
 * <p>
 * 该类实现了 {@link CheckParameterStrategy} 接口，用于处理带有 {@link BizXCheckLongMax} 注解的参数。
 * </p>
 *
 * @see CheckParameterStrategy
 * @see BizXCheckLongMax
 * @see BizXVerificationException
 * @see Common
 * @see Annotation
 *
 * @author francis
 * @since 2023-04-10 22:24
 */
@Slf4j
public class CheckParameterLongMaxHandler implements CheckParameterStrategy {

    /**
     * 获取当前处理器支持的注解类型
     *
     * @return 支持的注解类型 {@link BizXCheckLongMax}
     */
    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXCheckLongMax.class;
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

        if (annotation instanceof BizXCheckLongMax) {
            BizXCheckLongMax check = Common.to(annotation);
            if (value instanceof Long) {
                Long num = Common.to(value);
                if (num > check.max()) {
                    throw new BizXVerificationException(check.error().code(), check.error().message(), className, methodName, fieldName);
                }
            }
        }
    }
}
