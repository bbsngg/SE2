package com.example.cinema.controller.user;

import com.example.cinema.bl.user.BankAccountService;
import com.example.cinema.vo.BankCardForm;
import com.example.cinema.vo.BankCardVO;
import com.example.cinema.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "银行账户api")
@RestController
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    /**
     * 验证账户信息
     * @param bankCardForm
     * @return
     */
    @ApiOperation(value = "验证账户信息")
    @ApiImplicitParam(name = "bankCardForm",value = "银行卡Form",dataType = "BankCardForm")
    @PostMapping("/bank/login")
    public ResponseVO login(@RequestBody BankCardForm bankCardForm){
        BankCardVO bankCardVO=bankAccountService.login(bankCardForm);
        if(bankCardVO==null){
            return ResponseVO.buildFailure("用户名或密码错误");
        }
        return ResponseVO.buildSuccess(bankCardVO);
    }
}
