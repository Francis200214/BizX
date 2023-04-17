package com.biz.common.strategy;

/**
 * 策略模式父接口
 *
 * @param <Parameter>
 */
public interface StrategyService<Parameter> {

    boolean check(Parameter parameter);

}
