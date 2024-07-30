package com.biz.common.strategy;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.reflection.ReflectionUtils;
import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 策略模式匹配具体实例的抽象类，支持懒加载策略服务。
 * 使用泛型来通用化策略服务的参数类型和实现类类型。
 *
 * @param <Parameter_Type> 策略服务的参数类型。
 * @param <T>              策略服务的实现类类型，必须继承自StrategyService。
 * @author francis
 */
@Slf4j
public abstract class LazySelectableAbstractStrategyService<Parameter_Type, T extends StrategyService<Parameter_Type>> implements SelectableStrategyService<Parameter_Type, T> {

    /**
     * 使用ConcurrentHashMap来存储策略映射，确保线程安全。
     * key为参数类型的Class对象，value为对应参数类型的策略服务类列表。
     */
    private static ConcurrentMap<Class<?>, List<Class<? extends StrategyService>>> strategyCacheMap = new ConcurrentHashMap<>();

    private static final Lock initLock = new ReentrantLock();

    /**
     * 根据参数选择合适的策略服务实例。
     * 如果对应参数类型的策略服务尚未初始化，则进行初始化。
     *
     * @param param 选择策略的参数。
     * @return 符合条件的策略服务实例，如果找不到则返回null。
     */
    @Override
    public T slelect(Parameter_Type param) {
        Class<?> aClass = param.getClass();
        if (!strategyCacheMap.containsKey(aClass)) {
            initStrategyCacheMap(aClass);
        }

        for (Class<? extends StrategyService> aClass1 : strategyCacheMap.get(aClass)) {
            StrategyService bean = BizXBeanUtils.getBean(aClass1);
            if (bean.check(param)) {
                return Common.to(bean);
            }
        }
        return null;
    }

    /**
     * 泛型初始化策略映射表。
     * 通过反射遍历所有Bean，找出实现StrategyService接口且泛型匹配的类，加入到映射表中。
     * 此方法为同步方法，确保初始化过程的线程安全。
     *
     * @param param 泛型参数的Class对象，用于查找匹配的策略服务。
     */
    private void initStrategyCacheMap(Class<?> param) {
        if (strategyCacheMap.containsKey(param)) {
            return;
        }

        initLock.lock();
        try {
            // 获取所有的Bean定义类
            List<Class<?>> beanDefinitionClasses = BizXBeanUtils.getBeanDefinitionClasses();
            // 遍历所有类，找出实现StrategyService接口的类
            for (Class<?> aClass : ReflectionUtils.getImplementsClass(StrategyService.class, beanDefinitionClasses)) {
                // 遍历类实现的所有接口
                for (Class<?> anInterface : ReflectionUtils.getInterfaces(aClass)) {
                    // 排除直接实现StrategyService的接口
                    if (!ReflectionUtils.isImplementsClass(StrategyService.class, anInterface)) {
                        continue;
                    }
                    // 遍历接口的泛型参数
                    for (Class<?> interfaceGenericParameterizedType : ReflectionUtils.getInterfaceGenericParameterizedTypes(anInterface)) {
                        // 如果泛型参数匹配param类型，则将该类加入到映射表中
                        if (interfaceGenericParameterizedType.equals(param)) {
                            strategyCacheMap.computeIfAbsent(param, k -> new ArrayList<>()).add(Common.to(aClass));
                        }
                    }
                }
            }
        } finally {
            initLock.unlock();
        }

    }

}

