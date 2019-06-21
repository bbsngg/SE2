package com.example.cinema.bl.user;

import com.example.cinema.po.BankCard;
import com.example.cinema.vo.BankCardForm;
import com.example.cinema.vo.BankCardVO;
import com.example.cinema.vo.ResponseVO;

public interface BankAccountService {
    /**
     * 登陆银行账户
     * @param bankCardForm
     * @return
     */
    BankCardVO login(BankCardForm bankCardForm);
}
