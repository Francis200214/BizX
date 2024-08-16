package com.demo.service.impl;

import com.biz.operation.log.OperationLog;
import com.demo.log.LogTypeConstant;
import com.demo.service.IUserService;
import com.demo.service.bo.AddUserBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 *
 * @author francis
 * @since 1.0.1
 **/
@Service
@Slf4j
public class IUserServiceImpl implements IUserService {

    @Override
    @OperationLog(category = LogTypeConstant.USER_LOG, subcategory = LogTypeConstant.ADD_USER_LOG, content = "用户 operationName 在 #{T(java.time.LocalDateTime).now()} 操作了添加用户接口，" +
            "用户姓名 #{#addUserBo.name} 年龄 #{#addUserBo.age} 学校名称 #{#addUserBo.school}")
    public void addUser(AddUserBo addUserBo) {
//        try {
//            log.info("addUser {}", addUserBo);
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    @OperationLog(category = LogTypeConstant.USER_LOG, subcategory = LogTypeConstant.DELETE_USER_LOG, content = "用户 operationName 在 #{T(java.time.LocalDateTime).now()} 操作了删除用户接口，用户Id #{#userId}")
    @Override
    public void deleteUser(String userId) {
        log.info("deleteUser {}", userId);
    }

}
