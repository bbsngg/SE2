package com.example.cinema.blImpl.user;

import com.example.cinema.po.BankCard;
import com.example.cinema.vo.BankCardForm;
import com.example.cinema.vo.BankCardVO;

public interface BankAccountServiceForBl {
    /**
     * 根据银行卡id修改余额
     * @param bankCardId
     * @param fare
     * @return
     */
    String deduct(int bankCardId,double fare);

    /**
     * 根据id获取银行卡
     * @param id
     * @return
     */
    BankCard getBankCardById(int id);

    /**
     * 根据账户名获取银行卡
     * @param accountNumber
     * @return
     */
    BankCard getBankCardByAccountName(int accountNumber);
}
