package com.demo.strategy;

import org.springframework.stereotype.Service;

/**
 * @author francis
 * @create: 2023-04-17 09:16
 **/
@Service
public class ManImpl implements PeopleStrategyService {
    @Override
    public boolean check(PeopleEnum peopleEnum) {
        return PeopleEnum.男人 == peopleEnum;
    }

    @Override
    public void handle() {
        System.out.println("男人");
    }
}
