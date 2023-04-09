package com.biz.web.aop;

import com.biz.common.utils.Common;
import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.BizXApiCheck;
import com.biz.web.annotation.BizXApiCheckString;
import com.biz.web.core.VerificationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * 实现校验String类型的入参
 *
 * @author francis
 * @create: 2023-04-08 10:41
 **/
@Aspect
@BizXComponent
public class AbstractBizXCheckString {

    @Inject
    private CheckParameterService checkParameterService;

    @Around("@annotation(check)")
    public Object paramCheck(ProceedingJoinPoint joinPoint, BizXApiCheck check) throws Throwable {
        // 获取方法传入参数数组
        final Object[] args = joinPoint.getArgs();
        MethodSignature signature = Common.to(joinPoint.getSignature());
        final Parameter[] parameters = signature.getMethod().getParameters();
        int parametersLength = parameters.length, i = 0;
        //循环数组
        for (; i < parametersLength; i++) {
            Parameter parameter = parameters[i];
            parameter.getAnnotations();

//            checkParameterService.handle(parameter, args);

            //处理类似String Integer的类
            if (isPrimite(parameter.getType())) {
                //获取参数上是否带有自定义注解，不为空则代表有
                BizXApiCheckString annotation = parameter.getAnnotation(BizXApiCheckString.class);
                if (annotation == null) {
                    continue;
                }
                //判断传入参数是否为null
//                if (args[i] == null) {
//                    //抛出自定义异常，会被我的全局异常处理捕获，返回固定的返回体
//                    throw new RuntimeException("参数为Null");
//                }
                //利用反射，调用自定义注解中的参数方法
                Method verificationUtil = VerificationUtils.class.getMethod(annotation.value(), Object.class);
                Object invoke = verificationUtil.invoke(null, args[i]);
//                if (invoke.equals(false)) {
//                    throw new RuntimeException();
//                }
                continue;
            }
            //处理自定义实体类中带有自定义注解的成员变量，验证方法相同
            Class<?> paramClazz = parameter.getType();
            Object arg = Arrays.stream(args).filter(ar -> paramClazz.isAssignableFrom(ar.getClass())).findFirst().get();
            Field[] declaredFields = paramClazz.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                BizXApiCheckString annotation = field.getAnnotation(BizXApiCheckString.class);
                if (annotation == null) {
                    continue;
                }
//                if (args[i] == null) {
//                    throw new RuntimeException();
//                }
                Method verificationUtil = VerificationUtils.class.getMethod(annotation.value(), Object.class);
                Object invoke = verificationUtil.invoke(null, field.get(arg));
//                if (invoke.equals(false)) {
//                    throw new RuntimeException();
//                }
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 判断是否为基本类型：包括String
     *
     * @param clazz clazz
     * @return true：是; false：不是
     */
    private boolean isPrimite(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == String.class;
    }

}
