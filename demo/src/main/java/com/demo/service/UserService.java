package com.demo.service;

import com.biz.operation.log.OperationLog;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @OperationLog(category = "USER_OPERATION", subcategory = "LOGIN",
            content = "'User: ' + #username + ' attempted to login with IP: ' + #ip")
    public void login(String username, String password, String ip) {
        // 用户登录的逻辑
        // ...
    }
}