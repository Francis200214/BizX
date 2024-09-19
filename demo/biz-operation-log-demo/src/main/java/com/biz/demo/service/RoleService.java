package com.biz.demo.service;

/**
 * 角色服务
 *
 * @author francis
 * @create 2024-09-19
 **/
public interface RoleService {

    /**
     * 添加角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void addRole(String userId, String roleId);

    /**
     * 移除角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void removeRole(String userId, String roleId);

}
