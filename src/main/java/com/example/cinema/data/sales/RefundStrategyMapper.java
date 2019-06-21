package com.example.cinema.data.sales;

import com.example.cinema.po.RefundStrategy;
import com.example.cinema.vo.RefundStrategyForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.*;
import java.util.List;
@Mapper
public interface RefundStrategyMapper {

    int insertRefundStrategy(RefundStrategyForm refundStrategyForm);

    int updateRefundStrategy(RefundStrategyForm refundStrategyForm);

    int deleteRefundStrategyById(int id);
    
    List<RefundStrategy> selectAllRefundStrategy();
    
    RefundStrategy selectRefundStrategyById(int id);
}
