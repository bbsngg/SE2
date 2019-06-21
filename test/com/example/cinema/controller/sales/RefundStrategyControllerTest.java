package com.example.cinema.controller.sales;

import com.example.cinema.vo.RefundStrategyForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RefundStrategyControllerTest {

    @Autowired
    RefundStrategyController c;

    @Test
    public void publishRefundStrategy() {
        RefundStrategyForm f = new RefundStrategyForm();
        f.setPercent(0.7);
        f.setStartTime(48.0);
        f.setEndTime(22.0);

        System.out.println(c.publishRefundStrategy(f).getContent());
    }

    @Test
    public void updateRefundStrategy() {
    }

    @Test
    public void searchOneStrategyById() {
    }

    @Test
    public void searchAllStrategy() {
    }

    @Test
    public void deleteOneStrategyById() {
    }
}