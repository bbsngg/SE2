package com.example.cinema.bl.sales;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.example.cinema.vo.RefundStrategyForm;
import com.example.cinema.vo.ResponseVO;

public interface RefundStrategyService {

    //管理员增加一种退票策略
    ResponseVO publishRefundStrategy(RefundStrategyForm refundStrategyForm);

    //管理员更新一种退票策略
    ResponseVO updateRefundStrategy(RefundStrategyForm refundStrategyForm);

    //通过id搜索一种策略
    ResponseVO getRefundStrategyById(int id);

    //获取所有策略
    ResponseVO getAllRefundStrategy();

    //通过id删除一种策略
    ResponseVO deleteRefundStrategyById(int id);
}
