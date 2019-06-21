package com.example.cinema.blImpl.promotion.activity;

import com.example.cinema.po.promotion.Activity;
import com.example.cinema.vo.promotion.ActivityForm;
import com.example.cinema.vo.promotion.ActivityVO;
import com.example.cinema.vo.promotion.CouponForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)

public class ActivityServiceImplTest {

    @Autowired
    ActivityServiceImpl service;

    @Test
    public void publishActivity() {
        ActivityForm f = new ActivityForm();
        newCoupon();
        List<Integer> movies = new ArrayList<>(); movies.add(11);movies.add(12);movies.add(13);movies.add(14);
        CouponForm cf = this.newCoupon();
        f.setName("TestAct");
        f.setDescription("用于测试");
        f.setMovieList(movies);
        f.setCouponForm(cf);
        f.setStartTime(new Timestamp(System.currentTimeMillis()));
        f.setEndTime(new Timestamp(119, 6, 22, 24, 0, 0, 0));
        System.out.println(((Activity)service.publishActivity(f).getContent()).getStartTime());
    }

    public CouponForm newCoupon(){
        CouponForm cf = new CouponForm();
        cf.setName("测试优惠券");
        cf.setDescription("测试优惠券");
        cf.setTargetAmount(100.0);
        cf.setDiscountAmount(30.0);
        cf.setStartTime(new Timestamp(System.currentTimeMillis()));
        cf.setEndTime(new Timestamp(119, 6, 22, 24, 0, 0, 0));
        System.out.println(cf.getStartTime());
        return cf;
    }
    @Test
    public void getActivities() {
        ((List<ActivityVO>)service.getActivities().getContent()).forEach(item-> {

            System.out.println(item.getId());
        });
    }

    @Test
    public void getActivityById() {
        System.out.println(((ActivityVO)service.getActivityById(14).getContent()).getName());
    }

    @Test
    public void selectActivitiesByMovie() {
        ((List<Activity>)service.selectActivitiesByMovie(11)).forEach(item->{
            System.out.println(item.getName());
        });
    }
}