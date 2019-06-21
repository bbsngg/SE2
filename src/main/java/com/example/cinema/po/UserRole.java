package com.example.cinema.po;

public class UserRole {
    private Integer id;
    private String username;
    private String roleName;

    public UserRole(int id,String username,String roleName){
        this.id=id;
        this.username=username;
        this.roleName=roleName;
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
