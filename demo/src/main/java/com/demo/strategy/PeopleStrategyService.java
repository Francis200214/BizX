package com.demo.strategy;

import com.biz.common.strategy.StrategyService;

/**
 * @author francis
 * @since 1.0.1
 **/
public interface PeopleStrategyService extends StrategyService<PeopleEnum> {

    void handle();

}





