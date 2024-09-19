package com.biz.demo.service;

import com.biz.demo.bo.UserInfoBo;

/**
 * 用户服务。
 *
 * @author francis
 * @create 2024-09-19
 **/
public interface UserService {

    /**
     * 添加用户。
     *
     * @param userInfoBo 用户信息。
     * @return 用户ID。
     */
    String addUser(UserInfoBo userInfoBo);

    /**
     * 删除用户。
     *
     * @param userId 用户ID。
     */
    void removeUser(String userId);

    /**
     * 查询用户。
     *
     * @param userId 用户ID。
     * @return 用户信息。
     */
    UserInfoBo findUser(String userId);

}
