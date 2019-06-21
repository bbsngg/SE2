package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.UserRoleService;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.data.user.UserRoleMapper;
import com.example.cinema.po.User;
import com.example.cinema.po.UserRole;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserRoleForm;
import com.example.cinema.vo.UserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService,UserRoleServiceForBl{
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    AccountMapper accountMapper;

    @Override
    public ResponseVO getRoleByName(String username) {
        try{
            UserRoleVO userRoleVO=new UserRoleVO(userRoleMapper.selectRoleByName(username));
            return ResponseVO.buildSuccess(userRoleVO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("根据用户名获取角色失败");
        }
    }

    @Override
    public ResponseVO getRoleById(int id) {
        try{
            UserRoleVO userRoleVO=new UserRoleVO(userRoleMapper.selectRoleById(id));
            return ResponseVO.buildSuccess(userRoleVO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("根据id获取角色失败");
        }
    }

    @Override
    public ResponseVO getAllRoles() {
        List<UserRoleVO> userRoleVOList=new ArrayList<>();
        try{
            List<UserRole> userRoleList=userRoleMapper.selectAllRoles();
            for(UserRole userRole:userRoleList){
                userRoleVOList.add(new UserRoleVO(userRole));
            }
            return ResponseVO.buildSuccess(userRoleList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("获取所有角色失败");
        }
    }

    @Override
    public ResponseVO deleteOneRoleById(int id) {
        try{
            UserRole userRole=userRoleMapper.selectRoleById(id);
            User user=accountMapper.getAccountByName(userRole.getUsername());
            userRoleMapper.deleteOneRoleById(id);
            accountMapper.deleteAccountById(user.getId());
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("删除角色失败");
        }
    }

    @Override
    public ResponseVO updateOneRole(UserRoleForm userRoleForm) {
        try{
            userRoleMapper.updateOneRole(userRoleForm);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("更新角色失败");
        }
    }

    @Override
    public ResponseVO addOneRole(UserRole userRole) {
        try{
            userRoleMapper.insertOneRole(userRole);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }
        return null;
    }
}
