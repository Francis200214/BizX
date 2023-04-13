package com.biz.web.aop;

import com.biz.common.reflection.ReflectionUtils;
import com.biz.common.reflection.model.FieldModel;
import com.biz.common.utils.Common;
import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.BizXEnableApiCheck;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * 校验入参
 *
 * @author francis
 * @create: 2023-04-08 10:41
 **/
@Aspect
@BizXComponent
public class AbstractBizXCheckParameter {

    @Inject
    private CheckParameterFactory checkParameterFactory;

    /**
     * 检查接口上有 BizXEnableApiCheck 注解的方法
     */
    @Around("@annotation(check)")
    public Object paramCheck(ProceedingJoinPoint joinPoint, BizXEnableApiCheck check) throws Throwable {
        // 获取方法传入参数数组
        final Object[] args = joinPoint.getArgs();
        MethodSignature signature = Common.to(joinPoint.getSignature());
        // 获取方法的参数
        final Parameter[] parameters = signature.getMethod().getParameters();

        for (int parametersLength = parameters.length, i = 0; i < parametersLength; i++) {
            Parameter parameter = parameters[i];
            // 获取方法的参数类型
            Class<?> type = parameter.getType();

            // 参数不是自定义类
            if (type.getClassLoader() == null) {
                Object arg = args[i];
                for (Annotation annotation : type.getAnnotations()) {
                    checkParameterFactory.handle(annotation, arg);
                }

            } else {
                Object arg = args[i];
                // 获取自定义对象的所有字段
                for (FieldModel field : ReflectionUtils.getFields(type)) {
                    // 属性字段中的值
                    Object byFieldValue = ReflectionUtils.getByFieldValue(field.getField(), arg);
                    for (Annotation annotation : field.getAnnotations()) {
                        checkParameterFactory.handle(annotation, byFieldValue);
                    }
                }
            }

        }

        return joinPoint.proceed();
    }


}
