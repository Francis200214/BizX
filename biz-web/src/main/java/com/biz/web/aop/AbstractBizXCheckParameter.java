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
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Set;

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
        // 获取方法的参数
        final Parameter[] parameters = signature.getMethod().getParameters();

        for (int parametersLength = parameters.length, i = 0; i < parametersLength; i++) {
            Parameter parameter = parameters[i];
            // 获取方法的参数类型
            Class<?> type = parameter.getType();




            // 获取当前参数中所有的属性字段
            for (FieldModel field : ReflectionUtils.getFields(type)) {
                // 获取属性字段的所有注解
                for (Annotation annotation : field.getAnnotations()) {
                    checkParameterFactory.handle(annotation, args[i]);
                }
            }


            if (type.getClassLoader() != null) {
                // 获取自定义对象的所有字段
                Field[] declaredFields = type.getDeclaredFields();
                Object arg = args[i];
//                for (Field declaredField : declaredFields) {
//                    // 判断该字段上是否有注解
//                    if (declaredField.getAnnotation(EmailFormatCheck.class) != null) {
//                        EmailFormatCheck emailFormatCheck = declaredField.getAnnotation(EmailFormatCheck.class);
//                        if (declaredField.getType() == String.class) {
//                            // 取消字段的安全访问检查，使能够对字段进行操作
//                            declaredField.setAccessible(true);
//                            // 获取该字段的值
//                            String fieldValue = String.valueOf(declaredField.get(arg));
//                            // 检查该字段是否符合邮箱的格式
//                            if (!fieldValue.matches(Consts.EMAIL_FORMAT)) {
//                                return ResponseUtils.fail(emailFormatCheck.msg());
//                            }
//                        } else {
//                            return ResponseUtils.fail("参数类型不正确！");
//                        }
//                    }
//                }
            }

        }
        return joinPoint.proceed();
    }

//    public static boolean checkType(Class<?> o) {
//        return o == String.class || o == Long.class || o == Integer.class || o == Double.class || o == Float.class || o == Byte.class ||
//    }


}
