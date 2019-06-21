package com.example.cinema.data.user;

import com.example.cinema.po.BankCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface BankAccountMapper {
    /**
     * 根据账户名获取银行卡
     * @param accountNumber
     * @return
     */
    BankCard getAccountByName(@Param("accountNumber") int accountNumber);

    /**
     * 根据id获取银行卡
     * @param id
     * @return
     */
    BankCard getAccountById(@Param("id") int id);

    /**
     * 更新银行卡余额
     * @param id
     * @param balance
     * @return
     */
    int updateCardBalance(@Param("id") int id,@Param("balance") double balance);
}
