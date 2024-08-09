package com.biz.verification.handler;

import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXCheckSize;
import com.biz.verification.error.BizXVerificationException;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * 具体实现检查长度的处理器
 * <p>
 * 该类实现了 {@link CheckParameterStrategy} 接口，用于处理带有 {@link BizXCheckSize} 注解的参数。
 * </p>
 *
 * @see CheckParameterStrategy
 * @see BizXCheckSize
 * @see BizXVerificationException
 * @see Common
 * @see Annotation
 * @see Collection
 *
 * @author francis
 * @since 2023-04-10 22:24
 */
@Slf4j
public class CheckParameterSizeHandler implements CheckParameterStrategy {

    /**
     * 获取当前处理器支持的注解类型
     *
     * @return 支持的注解类型 {@link BizXCheckSize}
     */
    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXCheckSize.class;
    }

    /**
     * 检查注解和参数是否满足注解的要求
     *
     * @param annotation 注解实例
     * @param o          被检查的参数
     * @throws BizXVerificationException 如果参数不满足注解的要求，则抛出此异常
     */
    @Override
    public void check(Annotation annotation, Object o) throws BizXVerificationException {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXCheckSize) {
            BizXCheckSize check = Common.to(annotation);
            if (o instanceof Collection) {
                Collection<?> num = Common.to(o);
                if (num.size() < check.min() || num.size() > check.max()) {
                    throwError(check);
                }
                return;
            }

            if (o instanceof String) {
                String num = Common.to(o);
                if (num.length() < check.min() || num.length() > check.max()) {
                    throwError(check);
                }
            }
        }
    }

    private static void throwError(BizXCheckSize check) throws BizXVerificationException {
        throw new BizXVerificationException(check.error().code(), check.error().message());
    }

}
