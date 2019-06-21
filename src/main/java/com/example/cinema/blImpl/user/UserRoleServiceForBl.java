package com.example.cinema.blImpl.user;

import com.example.cinema.po.UserRole;
import com.example.cinema.vo.ResponseVO;

public interface UserRoleServiceForBl {
    /**
     * 添加角色
     * @param userRole
     * @return
     */
    ResponseVO addOneRole(UserRole userRole);
    /**
     * 根据用户名获取角色
     * @param username
     * @return
     */
    String getRoleByName(String username);
}
