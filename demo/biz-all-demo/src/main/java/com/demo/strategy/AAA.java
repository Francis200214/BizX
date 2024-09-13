package com.demo.strategy;

import org.springframework.stereotype.Service;

/**
 * @author francis
 * @since 1.0.1
 **/
@Service
public class AAA implements ABStrategyService {
    @Override
    public boolean check(ABEnum abEnum) {
        return ABEnum.AAA == abEnum;
    }
}