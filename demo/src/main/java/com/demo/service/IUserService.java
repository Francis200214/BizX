package com.demo.service;

import com.demo.service.bo.AddUserBo;

/**
 * 用户服务
 *
 * @author francis
 **/
public interface IUserService {

    /**
     * 添加用户信息
     * @param addUserBo 用户信息
     */
    void addUser(AddUserBo addUserBo);

    /**
     * 根据用户Id删除用户信息
     *
     * @param userId 用户Id
     */
    void deleteUser(String userId);


}
