package com.biz.demo.service.impl;

import com.biz.demo.service.RoleService;
import com.biz.operation.log.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色服务实现
 *
 * @author francis
 * @version 1.0.1
 * @create 2024-09-19
 * @since 1.0.1
 **/
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Override
    @OperationLog(category = "ROLE", subcategory = "ADD_ROLE", content = "添加的角色信息为: #{#userId} - #{#roleId}")
    public void addRole(String userId, String roleId) {
         log.info("[addRole] 添加角色成功");
    }

    @Override
    @OperationLog(category = "ROLE", subcategory = "REMOVE_ROLE", content = "删除的角色ID为: #{#userId} - #{#roleId}")
    public void removeRole(String userId, String roleId) {
        log.info("[removeRole] 删除角色成功");
    }

}
