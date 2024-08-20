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
 * <p>
 * 该类使用泛型来通用化策略服务的参数类型和实现类类型，从而提高系统的灵活性和扩展性。
 * 通过缓存机制和反射机制，能够动态选择和加载符合条件的策略服务实例。
 * </p>
 * <p>
 * 该类实现了{@link SelectableStrategyService}接口，提供了一种懒加载和线程安全的方式来管理策略服务的选择和初始化过程。
 * 子类可以通过继承此类，并实现自己的具体逻辑，来灵活地使用策略模式。
 * </p>
 *
 * @param <Parameter_Type> 策略服务的参数类型。
 * @param <T>              策略服务的实现类类型，必须继承自{@link StrategyService}。
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
@Slf4j
public abstract class LazySelectableAbstractStrategyService<Parameter_Type, T extends StrategyService<Parameter_Type>> implements SelectableStrategyService<Parameter_Type, T> {

    /**
     * 策略初始化器实例，用于懒加载和缓存策略服务。
     */
    private final StrategyInitializer<Parameter_Type> strategyInitializer = new StrategyInitializer<>();

    /**
     * 根据参数选择合适的策略服务实例。
     * <p>
     * 如果策略缓存尚未初始化，则调用初始化方法进行懒加载。
     * 然后遍历缓存的策略服务类，找到符合条件的策略服务实例并返回。
     * </p>
     *
     * @param param 策略选择的参数，其类型应与Parameter_Type一致。
     * @return 符合条件的策略服务实例，如果找不到则返回null。
     */
    @Override
    public T select(Parameter_Type param) {
        Class<?> aClass = param.getClass();
        if (strategyInitializer.getStrategies(aClass) == null) {
            strategyInitializer.initStrategyCacheMap(aClass);
        }

        for (Class<? extends StrategyService> strategyClass : strategyInitializer.getStrategies(aClass)) {
            StrategyService bean = BizXBeanUtils.getBean(strategyClass);
            if (bean.check(param)) {
                return Common.to(bean);
            }
        }
        return null;
    }
}
