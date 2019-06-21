package com.example.cinema.blImpl.promotion.coupon;

import com.example.cinema.po.promotion.Coupon;
import com.example.cinema.vo.promotion.CouponForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CouponServiceImplTest {

    @Autowired
    CouponServiceImpl service;

    @Test
    public void getCouponsByUser() { //会进行时间判断
        List<Coupon> list = (List<Coupon>)service.getCouponsByUser(15).getContent();
        System.out.println(service.getCouponsByUser(15).getContent());
        System.out.println(list.size());
        list.forEach(item->{
            System.out.println(item.getName());
        });
    }

    @Test
    public void addCoupon() {
        CouponForm f = newCoupon();
        service.addCoupon(f);
    }

    public CouponForm newCoupon(){
        CouponForm cf = new CouponForm();
        cf.setName("测试优惠券@@");
        cf.setDescription("测试优惠券@@");
        cf.setTargetAmount(100.0);
        cf.setDiscountAmount(30.0);
        cf.setStartTime(new Timestamp(System.currentTimeMillis()));
        cf.setEndTime(new Timestamp(119, 6, 22, 24, 0, 0, 0));
        System.out.println(cf.getStartTime());
        return cf;
    }

    @Test
    public void issueCoupon() {
        service.issueCoupon(17,15);
    }

    @Test
    public void selectCouponByUser() {
        service.selectCouponByUser(15).forEach(item->{
            System.out.println(item.getName());
        });
    }

    @Test
    public void insertCouponUser() {
        service.issueCoupon(17,15);
    }

    @Test
    public void deleteCouponUser() {
        service.deleteCouponUser(17,15);
    }

    @Test
    public void addCouponForBl() {
        CouponForm f = newCoupon();
        service.addCouponForBl(f);
    }

    @Test
    public void selectById() {
        System.out.println(service.selectById(18).getName());
    }
}