package com.example.cinema.vo;

import com.example.cinema.po.User;
import com.example.cinema.po.UserRole;

public class UserRoleVO {
    private Integer id;
    private String username;
    private String roleName;

    public UserRoleVO(User user){
        this.id=user.getId();
        this.username=user.getUsername();
    }

    public UserRoleVO(UserRole userRole){
        this.id=userRole.getId();
        this.username=userRole.getUsername();
        this.roleName=userRole.getRoleName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
