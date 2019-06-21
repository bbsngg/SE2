package com.example.cinema.controller.user;

import com.example.cinema.bl.user.UserRoleService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserRoleForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "角色api")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    UserRoleService userRoleService;

    /**
     * 获取所有账号角色
     * @return
     */
    @ApiOperation(value = "获取所有账号角色")
    @GetMapping("/all")
    public ResponseVO searchAllRoles(){
        return userRoleService.getAllRoles();
    }

    /**
     * 根据id获取角色
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取角色")
    @ApiImplicitParam(name = "id",value = "角色id",dataType = "Integer")
    @GetMapping("{id}")
    public ResponseVO searchOneRoleById(@PathVariable int id){
        return userRoleService.getRoleById(id);
    }

    /**
     * 更新角色
     * @param userRoleForm
     * @return
     */
    @ApiOperation(value = "更新角色")
    @ApiImplicitParam(name = "userRoleForm",value = "角色Form",dataType = "UserRoleForm")
    @PostMapping("/update")
    public ResponseVO updateRole(@RequestBody UserRoleForm userRoleForm){
        return userRoleService.updateOneRole(userRoleForm);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @ApiOperation(value = "删除角色")
    @ApiImplicitParam(name = "id",value = "角色id",dataType = "Integer")
    @DeleteMapping("/delete")
    public ResponseVO deleteRoleById(@RequestParam int id){
        return userRoleService.deleteOneRoleById(id);
    }
}
