package com.biz.verification.handler;

import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXCheckCollectionIsEmpty;
import com.biz.verification.error.BizXVerificationException;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * 检查 Collection 是否为空的具体实现类。
 * <p>实现了 {@link CheckParameterStrategy} 接口，提供了对 {@link BizXCheckCollectionIsEmpty} 注解的处理逻辑。</p>
 *
 * @see BizXCheckCollectionIsEmpty
 * @see BizXVerificationException
 * @see CheckParameterStrategy
 * @since 2023-04-08
 * @version 1.0.0
 * @author francis
 **/
@Slf4j
public class CheckParameterCollectionIsEmptyHandler implements CheckParameterStrategy {

    /**
     * 获取检查的注解类型。
     *
     * @return 需要检查的注解类型 {@link BizXCheckCollectionIsEmpty}
     */
    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXCheckCollectionIsEmpty.class;
    }

    /**
     * 检查参数是否符合注解的要求。
     *
     * @param annotation 需要检查的注解实例
     * @param value          需要检查的对象
     * @param className 类名
     * @param methodName 方法名
     * @param fieldName 参数名称
     * @throws BizXVerificationException 如果检查不通过，则抛出此异常
     */
    @Override
    public void check(Annotation annotation, Object value, String className, String methodName, String fieldName) throws BizXVerificationException {
        // 为 null 时不做处理
        if (value == null) {
            return;
        }
        if (annotation instanceof BizXCheckCollectionIsEmpty) {
            BizXCheckCollectionIsEmpty check = Common.to(annotation);
            if (value instanceof Collection) {
                if (!check.isEmpty() && isEmpty(Common.to(value))) {
                    throw new BizXVerificationException(check.error().code(), check.error().message(), className, methodName, fieldName);
                }
            }
        }
    }

    /**
     * 判断集合是否为空。
     *
     * @param collection 需要检查的集合
     * @return 如果集合为空，返回 true；否则返回 false
     */
    private static boolean isEmpty(Collection<?> collection) {
        return Common.isEmpty(collection);
    }

}
