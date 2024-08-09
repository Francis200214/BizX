package com.biz.verification;

import com.biz.common.reflection.ReflectionUtils;
import com.biz.common.reflection.model.FieldModel;
import com.biz.common.utils.Common;
import com.biz.verification.annotation.BizXEnableCheck;
import com.biz.verification.condition.CheckScanPackageCondition;
import com.biz.verification.factory.CheckParameterFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * 校验入参的 AOP 切面类。
 * <p>该类通过 AOP 切面技术，在方法调用前对方法参数进行校验。</p>
 *
 * <p>使用 {@link BizXEnableCheck} 注解的方法将触发参数校验逻辑。</p>
 *
 * @author francis
 * @version 1.0
 * @see BizXEnableCheck
 * @see ReflectionUtils
 * @see ProceedingJoinPoint
 * @see CheckScanPackageCondition
 * @since 2023-04-08
 **/
@Aspect
public class AbstractBizXCheckParameter {

    /**
     * 校验工厂，用于处理参数校验逻辑。
     */
    private final CheckParameterFactory checkParameterFactory;

    /**
     * 构造器。
     */
    public AbstractBizXCheckParameter(CheckParameterFactory checkParameterFactory) {
        this.checkParameterFactory = checkParameterFactory;
    }

    /**
     * 检查接口上有 {@link BizXEnableCheck} 注解的方法，并进行参数校验。
     *
     * @param joinPoint 切入点
     * @param check     {@link BizXEnableCheck} 注解实例
     * @return 方法执行结果
     * @throws Throwable 如果在方法执行或参数校验过程中出现异常
     */
    @Around("@annotation(check)")
    public Object paramCheck(ProceedingJoinPoint joinPoint, BizXEnableCheck check) throws Throwable {
        // 获取方法的类名
        String className = joinPoint.getTarget().getClass().getName();

        // 判断是否配置特定扫描包路径，以及扫描该类的包路径是否符合配置
        if (!isScanPackage(className)) {
            return joinPoint.proceed();
        }

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



    /**
     * 判断是否扫描该类的包路径。
     *
     * @param className 类名
     * @return 如果类名在配置的包路径下，返回 true；否则返回 false
     */
    private boolean isScanPackage(String className) {
        if (CheckScanPackageCondition.hasText()) {
            return CheckScanPackageCondition.classNameInPackage(className);
        }
        return true;
    }

}
