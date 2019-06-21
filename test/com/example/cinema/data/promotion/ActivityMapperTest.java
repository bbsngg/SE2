package com.example.cinema.data.promotion;

import com.example.cinema.po.promotion.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ActivityMapperTest {

    @Autowired
    ActivityMapper mapper;
    @Test
    public void insertActivity() {
        Activity a = new Activity();
//        春季外卖节	春季外卖节	2019-04-24 01:55:59	5	2019-04-21 01:55:59

//        a.setCoupon("春季外卖节");
        a.setDescription("春季外卖节");
        a.setName("春季外卖节");
        a.setStartTime(Timestamp.valueOf("2019-04-24 01:55:59"));
        a.setEndTime(Timestamp.valueOf("2019-04-24 01:55:59"));

        System.out.println(mapper.insertActivity(a));
    }

    @Test
    public void insertActivityAndMovie() {
    }
}