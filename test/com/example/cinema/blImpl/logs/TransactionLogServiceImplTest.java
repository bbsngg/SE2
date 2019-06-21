package com.example.cinema.blImpl.logs;

import com.example.cinema.po.TransactionLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionLogServiceImplTest {

    @Autowired
    TransactionLogServiceImpl service;

    @Test
    public void getTransById() {
        System.out.println(service.getTransById(8).getId());
    }

    @Test
    public void addTran() {
        TransactionLog log = new TransactionLog();
        log.setUserId(1);
        log.setUses("0");
        log.setAmount(100.0);
        log.setMethod("vip");
        service.addTran(log);//充值
    }

    @Test
    public void getAllTrans() {
        service.getAllTrans().forEach(item->{
            System.out.println(item.getId());
        });
    }

    @Test
    public void getTransLog() {
        service.getTransLog().getContent();
    }
}