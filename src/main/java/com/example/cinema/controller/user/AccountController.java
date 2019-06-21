package com.example.cinema.controller.user;

import com.example.cinema.blImpl.user.AccountServiceImpl;
import com.example.cinema.config.InterceptorConfiguration;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserRoleVO;
import com.example.cinema.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Api(description = "账号api")
@RestController

public class AccountController {
    private final static String ACCOUNT_INFO_ERROR="用户名或密码错误";
    @Autowired
    private AccountServiceImpl accountService;

    /**
     * 登陆账号
     * @param userForm
     * @param session
     * @return
     */
    @ApiOperation(value = "登陆账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userForm", value = "用户Form",dataType = "UserForm"),
            @ApiImplicitParam(name = "session", value = "session",dataType = "HttpSession")
    })
    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm, HttpSession session){
        UserRoleVO user=accountService.login(userForm);
        if(user==null)
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        session.setAttribute(InterceptorConfiguration.SESSION_KEY,userForm);
        return ResponseVO.buildSuccess(user);
    }

    /**
     * 注册账号
     * @param userForm
     * @return
     */
    @ApiOperation(value = "注册账号")
    @ApiImplicitParam(name = "userForm",value = "用户Form",dataType = "UserForm")
    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserForm userForm){
        return accountService.registerAccount(userForm);
    }

    /**
     * 登出账号
     * @param session
     * @return
     */
    @ApiOperation(value = "登出账号")
    @ApiImplicitParam(name = "session",value = "session",dataType = "HttpSession")
    @PostMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return "index";
    }
    
    @GetMapping("/allUsers")
    public ResponseVO selectAllUsers(){
        return accountService.selectAllUsers();
    }
}
