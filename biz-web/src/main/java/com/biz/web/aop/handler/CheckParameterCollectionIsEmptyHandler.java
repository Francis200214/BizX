package com.biz.web.aop.handler;

import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckCollectionIsEmpty;

import java.lang.reflect.Parameter;

/**
 * 检查 Collection 是否为空 具体实现
 *
 * @author francis
 * @create: 2023-04-08 16:45
 **/
@BizXComponent
public class CheckParameterCollectionIsEmptyHandler implements CheckParameterStrategy {

    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckCollectionIsEmpty.class;
    }

    @Override
    public void check(Parameter parameter, Object o) throws Exception {
        //获取参数上是否带有自定义注解，不为空则代表有
//        BizXApiCheckString annotation = parameter.getAnnotation(BizXApiCheckString.class);
//        if (annotation == null) {
//            return;
//        }
//        if (annotation.isNull()) {
//
//        }
        //判断传入参数是否为null
//                if (args[i] == null) {
//                    //抛出自定义异常，会被我的全局异常处理捕获，返回固定的返回体
//                    throw new RuntimeException("参数为Null");
//                }
        // 利用反射，调用自定义注解中的参数方法
//        Method verificationUtil = VerificationUtils.class.getMethod(annotation.value(), Object.class);
//        Object invoke = verificationUtil.invoke(null, o);
//                if (invoke.equals(false)) {
//                    throw new RuntimeException();
//                }

    }
}
