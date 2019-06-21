package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.RefundStrategyService;
import com.example.cinema.data.sales.RefundStrategyMapper;
import com.example.cinema.po.RefundStrategy;
import com.example.cinema.vo.RefundStrategyForm;
import com.example.cinema.vo.RefundStrategyVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class RefundStrategyServiceImpl implements RefundStrategyService,RefundStrategyForBl {

    @Autowired
    RefundStrategyMapper refundStrategyMapper;

    /**
     * 管理员增加一种退票策略
     * @param refundStrategyForm
     * @return
     */
    @Override
    public ResponseVO publishRefundStrategy(RefundStrategyForm refundStrategyForm) {
        try{
            int ret = refundStrategyMapper.insertRefundStrategy(refundStrategyForm);
            if (ret!=1)
                throw new Exception();
            return ResponseVO.buildSuccess(ret);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    /**
     * 管理员更新一种退票策略
     * @param refundStrategyForm
     * @return
     */
    @Override
    public ResponseVO updateRefundStrategy(RefundStrategyForm refundStrategyForm) {
        try {
            int ret = refundStrategyMapper.updateRefundStrategy(refundStrategyForm);
            if (ret!=1)
                throw new Exception();
            return ResponseVO.buildSuccess(ret);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }
    }


    /**
     * 通过id搜索一种策略
     * @param id
     * @return
     */
    @Override
    public ResponseVO getRefundStrategyById(int id) {
        try{
            RefundStrategyVO refundStrategyVO = new RefundStrategyVO(refundStrategyMapper.selectRefundStrategyById(id));
            return ResponseVO.buildSuccess(refundStrategyVO);
        }catch (Exception e){
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 获取所有策略
     * @return
     */
    @Override
    public ResponseVO getAllRefundStrategy() {
        try{
            List<RefundStrategy> refundStrategyList = refundStrategyMapper.selectAllRefundStrategy();
            List<RefundStrategyVO> refundStrategyVOList = new ArrayList<>();
            for(RefundStrategy refundStrategy : refundStrategyList){
                RefundStrategyVO refundStrategyVO = new RefundStrategyVO(refundStrategy);
                refundStrategyVOList.add(refundStrategyVO);
            }
            return ResponseVO.buildSuccess(refundStrategyVOList);
        }catch (Exception e){
            return ResponseVO.buildFailure("搜索所有strategy失败");
        }
    }

    /**
     * 通过id删除一种策略
     * @param id
     * @return
     */
    @Override
    public ResponseVO deleteRefundStrategyById(int id) {
        try{
            int ret = refundStrategyMapper.deleteRefundStrategyById(id);
            if (ret!=1)
                throw new Exception();
            return ResponseVO.buildSuccess(ret);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("删除refundStrategy失败");
        }
    }


    /**
     * 【Bl】获取适宜的退票策略(的退票金额百分比)
     * @param time
     * @return
     */
    @Override
    public double getRefStratPercentByTime(long time) {

        if (time<=0) return 0; // 此票作废
        //正常流程
        List<RefundStrategy> list = refundStrategyMapper.selectAllRefundStrategy();
        for (RefundStrategy item : list) {
            if (item.getStartTime() >= time && item.getEndTime() < time)
                return item.getPercent();
        }
        return 1; // 在全款退票规定时间之内
    }
}
