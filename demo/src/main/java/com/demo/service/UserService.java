package com.demo.service;

import com.biz.web.log.Loggable;
import com.demo.log.LogTypeConstant;
import com.demo.service.bo.AddUserBo;

/**
 * 用户服务
 *
 * @author francis
 * @create 2024-06-03 13:50
 **/
public interface UserService {


    @Loggable(logLargeType = LogTypeConstant.USER_LOG, content = "用户 {operationName} 在 {now()} 操作了添加用户接口，" +
            "用户姓名 {#addUserBo.name} 年龄 {#addUserBo.age} 学校名称 {#addUserBo.school}")
    void addUser(AddUserBo addUserBo);


    @Loggable(logLargeType = LogTypeConstant.USER_LOG, content = "用户 {operationName} 在 {now()} 操作了删除用户接口，用户Id {userId}")
    void deleteUser(String userId);


}
