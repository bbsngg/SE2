package com.example.cinema.blImpl.sales;

import com.example.cinema.po.RefundStrategy;
import com.example.cinema.vo.RefundStrategyForm;
import com.example.cinema.vo.RefundStrategyVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class RefundStrategyServiceImplTest {

    @Autowired
    RefundStrategyServiceImpl service;

    @Test
    public void publishRefundStrategy() {

        RefundStrategyForm form = new RefundStrategyForm();
        form.setStartTime(24.0);
        form.setEndTime(3.0);
        form.setPercent(0.80);
        System.out.println(service.publishRefundStrategy(form).getContent());
    }

    @Test
    public void updateRefundStrategy() {
        RefundStrategyForm form = new RefundStrategyForm();
        form.setId(1);
        form.setStartTime(22.0);
        form.setEndTime(1.0);
        form.setPercent(0.60);
        System.out.println(service.updateRefundStrategy(form).getContent());

    }

    @Test
    public void getRefundStrategyById() {
        int id = 2;
        RefundStrategyVO vo = (RefundStrategyVO)service.getRefundStrategyById(id).getContent();
        System.out.println(vo.getId());
        System.out.println(vo.getPercent());
        System.out.println(vo.getEndTime());
        System.out.println(vo.getStartTime());
    }

    @Test
    public void getAllRefundStrategy() {
        List<RefundStrategyVO> list = (List<RefundStrategyVO>)service.getAllRefundStrategy().getContent();
        list.forEach(vo->{
            System.out.println("\nget");
            System.out.println(vo.getId());
            System.out.println(vo.getPercent());
            System.out.println(vo.getEndTime());
            System.out.println(vo.getStartTime());
        });
    }

    @Test
    public void deleteRefundStrategyById() {
//        int id = 3; //错误的输出
        int id = 1; //正确的输出
        System.out.println(service.deleteRefundStrategyById(id).getContent());
    }

    @Test
    public void getRefStratPercentByTime() {

        long time = 20;
        System.out.println(service.getRefStratPercentByTime(20));
        System.out.println(service.getRefStratPercentByTime(48));
    }
}