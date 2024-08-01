package com.demo.service.impl;

import com.biz.web.log.Loggable;
import com.demo.log.LogTypeConstant;
import com.demo.service.UserService;
import com.demo.service.bo.AddUserBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 *
 * @author francis
 * @since 2024-06-03 13:50
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    @Loggable(logLargeType = LogTypeConstant.USER_LOG, logSmallType = LogTypeConstant.ADD_USER_LOG, content = "用户 operationName 在 now() 操作了添加用户接口，" +
            "用户姓名 #{#addUserBo.name} 年龄 #{#addUserBo.age} 学校名称 #{#addUserBo.school}")
    public void addUser(AddUserBo addUserBo) {
//        log.info("addUser {}", addUserBo);
    }

    @Loggable(logLargeType = LogTypeConstant.USER_LOG, logSmallType = LogTypeConstant.DELETE_USER_LOG, content = "用户 operationName 在 now() 操作了删除用户接口，用户Id #{#userId}")
    @Override
    public void deleteUser(String userId) {
//        log.info("deleteUser {}", userId);
    }

}
