package com.example.cinema.blImpl.promotion.coupon;


import com.example.cinema.po.promotion.Coupon;
import com.example.cinema.vo.promotion.CouponForm;

import java.util.List;

/**
 * @author sdj
 * @data 2019/5/14 21:02 PM
 */
public interface CouponServiceForBl {

    //通过userid获取优惠券
    List<Coupon> selectCouponByUser(int userId);

    //增加userid用户的couponid优惠券
    String insertCouponUser(int couponId, int userId);

    //删除userid用户的couponid优惠券
    String deleteCouponUser(int couponId, int userId);

    //新增优惠券
    Coupon addCouponForBl(CouponForm couponForm);

    //couponid获取优惠券
    Coupon selectById(int id);
}
