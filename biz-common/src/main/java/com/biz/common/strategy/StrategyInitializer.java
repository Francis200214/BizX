package com.biz.common.strategy;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.reflection.ReflectionUtils;
import com.biz.common.utils.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 策略初始化器类，用于懒加载和缓存策略服务。
 * <p>
 * 该类通过反射机制和泛型类型匹配，将实现了{@link StrategyService}接口的策略服务类缓存起来，
 * 以便在运行时根据特定参数类型快速选择合适的策略服务。
 * </p>
 * <p>
 * 通过使用ConcurrentMap和ReentrantLock，确保在多线程环境下策略服务的初始化过程是线程安全的。
 * </p>
 *
 * @param <Parameter_Type> 策略服务的参数类型
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
public class StrategyInitializer<Parameter_Type> {

    /**
     * 用于存储策略映射的线程安全集合。
     * key为参数类型的Class对象，value为对应参数类型的策略服务类列表。
     */
    private final ConcurrentMap<Class<?>, List<Class<? extends StrategyService>>> strategyCacheMap;

    /**
     * 初始化策略映射表时使用的锁，确保线程安全。
     */
    private final Lock initLock = new ReentrantLock();

    /**
     * 构造函数，初始化策略缓存映射表。
     */
    public StrategyInitializer() {
        this.strategyCacheMap = new ConcurrentHashMap<>();
    }

    /**
     * 初始化策略缓存映射表。
     * <p>
     * 通过反射遍历所有Bean，找出实现了{@link StrategyService}接口且泛型匹配的类，并将其加入到策略缓存映射表中。
     * 该方法使用锁来确保多线程环境下的线程安全。
     * </p>
     *
     * @param param 泛型参数的Class对象，用于查找匹配的策略服务。
     */
    public void initStrategyCacheMap(Class<?> param) {
        if (strategyCacheMap.containsKey(param)) {
            return;
        }

        initLock.lock();
        try {
            List<Class<?>> beanDefinitionClasses = BizXBeanUtils.getBeanDefinitionClasses();
            for (Class<?> beanClass : ReflectionUtils.getImplementsClass(StrategyService.class, beanDefinitionClasses)) {
                for (Class<?> anInterface : ReflectionUtils.getInterfaces(beanClass)) {
                    if (!ReflectionUtils.isImplementsClass(StrategyService.class, anInterface)) {
                        continue;
                    }
                    for (Class<?> interfaceGenericParameterizedType : ReflectionUtils.getInterfaceGenericParameterizedTypes(anInterface)) {
                        if (interfaceGenericParameterizedType.equals(param)) {
                            strategyCacheMap.computeIfAbsent(param, k -> new ArrayList<>()).add(Common.to(beanClass));
                        }
                    }
                }
            }
        } finally {
            initLock.unlock();
        }
    }

    /**
     * 根据参数类型获取策略服务类列表。
     *
     * @param param 参数类型的Class对象。
     * @return 匹配参数类型的策略服务类列表，如果没有匹配的策略则返回null。
     */
    public List<Class<? extends StrategyService>> getStrategies(Class<?> param) {
        return strategyCacheMap.get(param);
    }

}
