package com.example.cinema.blImpl.logs;

import com.example.cinema.po.TransactionLog;
import com.example.cinema.vo.ResponseVO;

import java.util.List;

public interface TransactionLogServiceForBl {

    //通过记录id获取交易记录
    TransactionLog getTransById(int id);

    //增加一条交易记录
    void addTran(TransactionLog tran);

    //通过userid获取某用户的所有交易记录
    List<TransactionLog> getByUserIdForBl(int userId);

    //获取所有交易记录
    List<TransactionLog> getAllTrans();
}
