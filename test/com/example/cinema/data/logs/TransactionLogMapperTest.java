package com.example.cinema.data.logs;

import com.example.cinema.po.TransactionLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionLogMapperTest {

    @Autowired
    TransactionLogMapper mapper;

    @Test
    public void selectAllTrans() {
    }

    @Test
    public void selectTransById() {
    }

    @Test
    public void insertTran() {
        TransactionLog log = new TransactionLog();
        log.setUserId(1);
        log.setUses("1");
        log.setAmount(100.0);
        log.setMethod("1");
        mapper.insertTran(log);

    }
}