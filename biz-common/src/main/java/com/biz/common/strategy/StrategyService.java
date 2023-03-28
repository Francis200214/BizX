package com.biz.common.strategy;

/**
 * 策略模式
 *
 * @param <Parameter>
 */
public interface StrategyService<Parameter> {

    boolean check(Parameter parameter);

}
