package com.example.cinema.bl.user;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserRoleForm;

public interface UserRoleService {

    /**
     * 根据账号id获取角色
     * @param id
     * @return
     */
    ResponseVO getRoleById(int id);
    /**
     * 获取所有账号的角色
     * @return
     */
    ResponseVO getAllRoles();
    /**
     * 删除账号及角色信息
     * @param id
     * @return
     */
    ResponseVO deleteOneRoleById(int id);

    /**
     * 更新角色信息
     * @param userRoleForm
     * @return
     */
    ResponseVO updateOneRole(UserRoleForm userRoleForm);
}
