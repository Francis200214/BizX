package com.demo.strategy;

import com.biz.common.strategy.StrategyService;

/**
 * @author francis
 * @create: 2023-04-16 20:23
 **/
public interface PeopleStrategyService extends StrategyService<PeopleEnum> {

    void handle();

}





