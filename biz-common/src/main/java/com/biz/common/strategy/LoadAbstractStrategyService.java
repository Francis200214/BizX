package com.biz.common.strategy;

import com.biz.common.reflection.ReflectionUtils;
import com.biz.common.utils.BizXBeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 策略模式匹配具体实例
 *
 * @author francis
 * @create 2023/3/27 19:48
 */
public abstract class LoadAbstractStrategyService<Parameter_Type, T extends StrategyService<Parameter_Type>> implements LoadStrategyService<Parameter_Type, T> {

    private static Map<Class<?>, List<Class<Class<?>>>> strategyCatchMap = new HashMap<>();

    @Override
    public T slelect(Parameter_Type param) {
        return null;
    }


    private void selectAllExtendsBean() {
        // 获取所有的Bean
        List<Class<?>> beanDefinitionClasses = BizXBeanUtils.getBeanDefinitionClasses();
        // 获取所有实现 StrategyService接口 的 Class
        List<Class<?>> implementsClass = ReflectionUtils.getImplementsClass(StrategyService.class, beanDefinitionClasses);
        for (Class<?> aClass : implementsClass) {
            
        }
    }



}
