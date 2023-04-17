package com.demo.strategy;

import org.springframework.stereotype.Service;

/**
 * @author francis
 * @create: 2023-04-17 09:17
 **/
@Service
public class BBB implements ABStrategyService {
    @Override
    public boolean check(ABEnum abEnum) {
        return ABEnum.BBB == abEnum;
    }
}
