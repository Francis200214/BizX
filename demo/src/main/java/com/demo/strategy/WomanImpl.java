package com.demo.strategy;

import org.springframework.stereotype.Service;

/**
 * @author francis
 * @since 2023-04-17 09:16
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
