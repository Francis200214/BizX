package com.biz.web.aop;

import com.biz.common.utils.Common;
import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.BizXEnableApiCheck;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;

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

    @Around("@annotation(check)")
    public Object paramCheck(ProceedingJoinPoint joinPoint, BizXEnableApiCheck check) throws Throwable {
        // 获取方法传入参数数组
        final Object[] args = joinPoint.getArgs();
        MethodSignature signature = Common.to(joinPoint.getSignature());
        final Parameter[] parameters = signature.getMethod().getParameters();
        for (int parametersLength = parameters.length, i = 0; i < parametersLength; i++) {
            Parameter parameter = parameters[i];

            Class<?> paramClazz = parameter.getType();
            Object arg = Arrays.stream(args).filter(ar -> paramClazz.isAssignableFrom(ar.getClass())).findFirst().get();
            Field[] declaredFields = paramClazz.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                for (Annotation annotation : field.getAnnotations()) {
                    checkParameterFactory.handle(annotation, args[i]);
                }
//                if (args[i] == null) {
//                    throw new RuntimeException();
//                }
//                Method verificationUtil = VerificationUtils.class.getMethod(annotation.value(), Object.class);
//                Object invoke = verificationUtil.invoke(null, field.get(arg));
//                if (invoke.equals(false)) {
//                    throw new RuntimeException();
//                }
            }

            for (Annotation annotation : parameter.getAnnotations()) {
                checkParameterFactory.handle(annotation, args[i]);
            }
        }
        return joinPoint.proceed();
    }

}
