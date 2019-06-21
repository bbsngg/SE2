package com.example.cinema.blImpl.logs;

import com.example.cinema.bl.logs.TransactionLogService;
import com.example.cinema.data.logs.TransactionLogMapper;
import com.example.cinema.po.TransactionLog;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionLogServiceImpl implements TransactionLogService,TransactionLogServiceForBl {
    @Autowired
    TransactionLogMapper transactionLogMapper;

    /**
     * 【Bl】通过记录id获取交易记录
     * @param id
     * @return
     */
    @Override
    public TransactionLog getTransById(int id) {
        return transactionLogMapper.selectTransById(id);
    }

    /**
     * 【Bl】增加一条消费记录
     * @param tran
     */
    @Override
    public void addTran(TransactionLog tran) {
        transactionLogMapper.insertTran(tran);
    }

    /**
     * 【Bl】获取所有交易记录
     * @return
     */
    @Override
    public List<TransactionLog> getAllTrans() {
        return transactionLogMapper.selectAllTrans();
    }

    /**
     * 返回交易记录VO
     * @return
     */
    @Override
    public ResponseVO getTransLog() {
        List<TransactionLog> list = getAllTrans();
        return ResponseVO.buildSuccess(list);
    }

    /**
     * 通过用户id，返回记录VO
     * @param userId
     * @return
     */
    @Override
    public ResponseVO getByUserId(int userId) {
        List<TransactionLog> list = getAllTrans();
        List<TransactionLog> ret = new ArrayList<>();
        list.forEach(item -> {
            if (item.getUserId()==userId)
                ret.add(item);
        });
        return ResponseVO.buildSuccess(ret);
    }

    /**
     * 【BL】通过用户id，返回记录VO
     * @param userId
     * @return
     */
    @Override
    public List<TransactionLog> getByUserIdForBl(int userId) {
        List<TransactionLog> list = getAllTrans();
        List<TransactionLog> ret = new ArrayList<>();
        list.forEach(item -> {
            if (item.getUserId()==userId)
                ret.add(item);
        });
        return ret;
    }
}
