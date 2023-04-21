package com.biz.common.strategy;

import com.biz.common.reflection.ReflectionUtils;
import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 策略模式匹配具体实例
 *
 * @author francis
 * @create 2023/3/27 19:48
 */
@Slf4j
public abstract class LazySelectableAbstractStrategyService<Parameter_Type, T extends StrategyService<Parameter_Type>> implements SelectableStrategyService<Parameter_Type, T> {

    private static ConcurrentMap<Class<?>, List<Class<? extends StrategyService>>> strategyCatchMap = new ConcurrentHashMap<>();


    @Override
    public T slelect(Parameter_Type param) {
        Class<?> aClass = param.getClass();
        if (!strategyCatchMap.containsKey(aClass)) {
            initStrategyCatchMap(aClass);
        }

        for (Class<? extends StrategyService> aClass1 : strategyCatchMap.get(aClass)) {
            StrategyService bean = BizXBeanUtils.getBean(aClass1);
            if (bean.check(param)) {
                return (T) bean;
            }
        }
        return null;
    }


    /**
     * 泛型初始化
     *
     * @param param 泛型Class
     */
    private synchronized void initStrategyCatchMap(Class<?> param) {
        if (strategyCatchMap.containsKey(param)) {
            return;
        }

        // 获取所有的Bean
        List<Class<?>> beanDefinitionClasses = BizXBeanUtils.getBeanDefinitionClasses();
        // 获取所有实现 StrategyService接口 的 Class
        for (Class<?> aClass : ReflectionUtils.getImplementsClass(StrategyService.class, beanDefinitionClasses)) {
            // 获取 Class 实现的接口
            for (Class<?> anInterface : ReflectionUtils.getInterfaces(aClass)) {
                if (!ReflectionUtils.isImplementsClass(StrategyService.class, anInterface)) {
                    continue;
                }
                // 获取 Class 的所有泛型
                for (Class<?> interfaceGenericParameterizedType : ReflectionUtils.getInterfaceGenericParameterizedTypes(anInterface)) {
                    // 判断泛型是否是需要的 Class
                    if (interfaceGenericParameterizedType.equals(param)) {
                        strategyCatchMap.computeIfAbsent(param, k -> new ArrayList<>()).add(Common.to(aClass));
                    }
                }
            }
        }
    }


}
