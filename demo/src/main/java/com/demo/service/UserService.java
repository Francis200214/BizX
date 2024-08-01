package com.demo.service;

import com.demo.service.bo.AddUserBo;

/**
 * 用户服务
 *
 * @author francis
 * @since 2024-06-03 13:50
 **/
public interface UserService {


    void addUser(AddUserBo addUserBo);


    void deleteUser(String userId);


}
