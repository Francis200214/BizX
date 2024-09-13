package com.demo.strategy;

import org.springframework.stereotype.Service;

/**
 * @author francis
 * @since 1.0.1
 **/
@Service
public class WomanImpl implements PeopleStrategyService {
    @Override
    public boolean check(PeopleEnum peopleEnum) {
        return PeopleEnum.女人 == peopleEnum;
    }

    @Override
    public void handle() {
        System.out.println("女人");
    }
}
