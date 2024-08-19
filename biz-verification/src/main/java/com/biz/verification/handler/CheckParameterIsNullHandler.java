package com.biz.verification.handler;

import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXCheckIsNull;
import com.biz.verification.error.BizXVerificationException;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * 具体实现检查是否为 null 的处理器
 * <p>
 * 该类实现了 {@link CheckParameterStrategy} 接口，用于处理带有 {@link BizXCheckIsNull} 注解的参数。
 * </p>
 *
 * @see CheckParameterStrategy
 * @see BizXCheckIsNull
 * @see BizXVerificationException
 * @see Common
 * @see Annotation
 *
 * @author francis
 * @since 1.0.1
 */
@Slf4j
public class CheckParameterIsNullHandler implements CheckParameterStrategy {

    /**
     * 获取当前处理器支持的注解类型
     *
     * @return 支持的注解类型 {@link BizXCheckIsNull}
     */
    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXCheckIsNull.class;
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
        if (annotation instanceof BizXCheckIsNull) {
            BizXCheckIsNull check = Common.to(annotation);
            if (!check.isNull() && isNull(value)) {
                throw new BizXVerificationException(check.error().code(), check.error().message(), className, methodName, fieldName);
            }
        }
    }

    /**
     * 判断对象是否为 null
     *
     * @param o 被检查的对象
     * @return 如果对象为 null，则返回 true；否则返回 false
     */
    private static boolean isNull(Object o) {
        return o == null;
    }

}
