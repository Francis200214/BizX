package com.demo.strategy;

import org.springframework.stereotype.Service;

/**
 * @author francis
 * @since 2023-04-17 09:17
 **/
@Service
public class AAA implements ABStrategyService {
    @Override
    public boolean check(ABEnum abEnum) {
        return ABEnum.AAA == abEnum;
    }
}
