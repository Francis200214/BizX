package com.biz.common.strategy;

/**
 * 策略模式使用接口
 *
 * @param <Parameter_Type>
 * @param <T>
 */
public interface LoadStrategyService<Parameter_Type, T extends StrategyService<Parameter_Type>> {

    T slelect(Parameter_Type param);

}
