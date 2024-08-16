package com.demo.service;

import com.demo.service.bo.AddUserBo;

/**
 * 用户服务
 *
 * @author francis
 * @since 1.0.1
 **/
public interface IUserService {


    void addUser(AddUserBo addUserBo);


    void deleteUser(String userId);


}
