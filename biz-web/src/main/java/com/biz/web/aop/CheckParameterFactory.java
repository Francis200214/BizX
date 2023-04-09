package com.biz.web.aop;

import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.BizXApiCheckString;
import com.biz.web.aop.handler.CheckParameterStrategy;
import com.biz.web.core.VerificationUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 检查参数工厂
 *
 * 主要做注册 Bean 后将实现 CheckParameterStrategy 接口的实现类加入到 Map 中，
 * 方便后续使用 CheckParameterStrategy 下各个实现类
 *
 * @author francis
 * @create: 2023-04-08 17:05
 **/
@BizXComponent
public class CheckParameterFactory implements InitializingBean, ApplicationContextAware, CheckParameterService {

    private static final Map<Class<?>, CheckParameterStrategy> CHECK_PARAMETER_STRATEGY_MAP = new HashMap<>();

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        // 将 Spring 容器中所有的 CheckParameterStrategy 注册到 LOGIN_HANDLER_MAP
        for (CheckParameterStrategy handler : applicationContext.getBeansOfType(CheckParameterStrategy.class).values()) {
            CHECK_PARAMETER_STRATEGY_MAP.put(handler.getCheckAnnotation(), handler);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void handle(Parameter parameter, Object args) throws Throwable {
        CheckParameterStrategy checkParameterStrategy = CHECK_PARAMETER_STRATEGY_MAP.get(parameter.getType());
        if (checkParameterStrategy == null) {
            throw new RuntimeException("not found check parameter strategy");
        }
        // 处理操作具体类型的参数
        checkParameterStrategy.check(parameter, args);


        //处理类似String Integer的类
//        if (isPrimite(parameter.getType())) {
//            //获取参数上是否带有自定义注解，不为空则代表有
//            BizXApiCheckString annotation = parameter.getAnnotation(BizXApiCheckString.class);
//            if (annotation == null) {
//                return;
//            }
//            //判断传入参数是否为null
////                if (args[i] == null) {
////                    //抛出自定义异常，会被我的全局异常处理捕获，返回固定的返回体
////                    throw new RuntimeException("参数为Null");
////                }
//            //利用反射，调用自定义注解中的参数方法
//            Method verificationUtil = VerificationUtils.class.getMethod(annotation.value(), Object.class);
//            Object invoke = verificationUtil.invoke(null, args[i]);
////                if (invoke.equals(false)) {
////                    throw new RuntimeException();
////                }
//            return;
//        }
//        //处理自定义实体类中带有自定义注解的成员变量，验证方法相同
//        Class<?> paramClazz = parameter.getType();
//        Object arg = Arrays.stream(args).filter(ar -> paramClazz.isAssignableFrom(ar.getClass())).findFirst().get();
//        Field[] declaredFields = paramClazz.getDeclaredFields();
//        for (Field field : declaredFields) {
//            field.setAccessible(true);
//            BizXApiCheckString annotation = field.getAnnotation(BizXApiCheckString.class);
//            if (annotation == null) {
//                continue;
//            }
////                if (args[i] == null) {
////                    throw new RuntimeException();
////                }
//            Method verificationUtil = VerificationUtils.class.getMethod(annotation.value(), Object.class);
//            Object invoke = verificationUtil.invoke(null, field.get(arg));
////                if (invoke.equals(false)) {
////                    throw new RuntimeException();
////                }
//        }
    }

    private boolean isPrimite(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == String.class;
    }

}
