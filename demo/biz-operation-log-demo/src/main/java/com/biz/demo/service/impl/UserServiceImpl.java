package com.biz.demo.service.impl;

import com.biz.demo.bo.UserInfoBo;
import com.biz.demo.service.RoleService;
import com.biz.demo.service.UserService;
import com.biz.operation.log.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 *
 * @author francis
 * @create 2024-09-19
 **/
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleService roleService;

    @Override
    @OperationLog(category = "USER", subcategory = "ADD_USER", content = "添加的用户信息为: #{#userInfoBo}")
    public String addUser(UserInfoBo userInfoBo) {
        log.info("[addUser] 添加成功");
        roleService.addRole("1", "2");
        return "123";
    }

    @Override
    @OperationLog(category = "USER", subcategory = "REMOVE_USER", content = "删除的用户ID为: #{#userId}")
    public void removeUser(String userId) {
        log.info("[UserServiceImpl] 删除成功");
        roleService.removeRole("1", "2");
    }

    @Override
    @OperationLog(category = "USER", subcategory = "FIND_USER", content = "查询的用户ID为: #{#userId}")
    public UserInfoBo findUser(String userId) {
        log.info("[findUser] 查询成功");
        return null;
    }

}
