package com.biz.starter.service.impl;

import com.biz.starter.service.TestService;
import org.springframework.stereotype.Service;

import javax.inject.Named;


//@Named
public class TestServiceImpl implements TestService {


    @Override
    public boolean check() {
        return false;
    }
}
