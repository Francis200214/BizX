package com.biz.common.strategy;

/**
 * 策略模式匹配具体实例
 *
 * @author francis
 * @create 2023/3/27 19:48
 */
public abstract class LoadAbstractStrategyService<Parameter_Type, T extends StrategyService<Parameter_Type>> implements LoadStrategyService<Parameter_Type, T> {

    @Override
    public T slelect(Parameter_Type param) {
        return null;
    }




}
