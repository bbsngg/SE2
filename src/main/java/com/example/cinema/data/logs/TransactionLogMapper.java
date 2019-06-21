package com.example.cinema.data.logs;

import com.example.cinema.po.TransactionLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TransactionLogMapper {

    //获取表内所有交易记录
    List<TransactionLog> selectAllTrans();

    //通过id获取交易记录
    TransactionLog selectTransById(@Param("id") int id);

    //插入一条交易记录
    void insertTran(TransactionLog tran);
}
