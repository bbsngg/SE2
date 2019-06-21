package com.example.cinema.data.user;

import com.example.cinema.po.UserRole;
import com.example.cinema.vo.UserRoleForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    /**
     * 根据id获取一条role信息
     * @param id
     * @return
     */
    UserRole selectRoleById(int id);

    /**
     * 根据用户名获取一条role信息
     * @param username
     * @return
     */
    UserRole selectRoleByName(String username);

    /**
     * 获取所有role信息
     * @return
     */
    List<UserRole> selectAllRoles();

    /**
     * 根据id删除一条role信息
     * @param id
     * @return
     */
    int deleteOneRoleById(int id);

    /**
     * 更新role信息
     * @param userRoleForm
     * @return
     */
    int updateOneRole(UserRoleForm userRoleForm);

    /**
     * 插入一条role信息
     * @param userRole
     * @return
     */
    int insertOneRole(UserRole userRole);
}
