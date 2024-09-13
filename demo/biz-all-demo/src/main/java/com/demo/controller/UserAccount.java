package com.demo.controller;

import com.biz.web.account.BizAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * @author francis
 * @since 1.0.1
 **/
@Setter
@Getter
@ToString
public class UserAccount implements BizAccount<String> {

    private String userId;

    private String name;

    @Override
    public String getId() {
        return userId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<String> getRoles() {
        return null;
    }

}
