package com.example.cinema.bl.logs;

import com.example.cinema.vo.ResponseVO;

public interface TransactionLogService {

    //返回交易记录VO
    ResponseVO getTransLog();

    //通过用户id，返回记录VO
    ResponseVO getByUserId(int userId);
}
