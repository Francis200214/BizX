package com.biz.verification.handler;

import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXCheckDoubleMax;
import com.biz.verification.error.BizXVerificationException;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * 检查 Double 最大值的具体实现类。
 * <p>实现了 {@link CheckParameterStrategy} 接口，提供了对 {@link BizXCheckDoubleMax} 注解的处理逻辑。</p>
 *
 * @see BizXCheckDoubleMax
 * @see BizXVerificationException
 * @see CheckParameterStrategy
 * @since 2023-04-08
 * @version 1.0.0
 * @author francis
 **/
@Slf4j
public class CheckParameterDoubleMaxHandler implements CheckParameterStrategy {

    /**
     * 获取检查的注解类型。
     *
     * @return 需要检查的注解类型 {@link BizXCheckDoubleMax}
     */
    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXCheckDoubleMax.class;
    }

    /**
     * 检查参数是否符合注解的要求。
     *
     * @param annotation 需要检查的注解实例
     * @param o          需要检查的对象
     * @throws BizXVerificationException 如果检查不通过，则抛出此异常
     */
    @Override
    public void check(Annotation annotation, Object o) throws BizXVerificationException {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXCheckDoubleMax) {
            BizXCheckDoubleMax check = Common.to(annotation);
            if (o instanceof Double) {
                Double num = Common.to(o);
                if (num > check.max()) {
                    throw new BizXVerificationException(check.error().code(), check.error().message());
                }
            }
        }
    }
}
