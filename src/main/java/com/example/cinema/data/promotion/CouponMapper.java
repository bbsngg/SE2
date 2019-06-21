package com.example.cinema.data.promotion;

import com.example.cinema.po.promotion.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/17.
 */
@Mapper
public interface CouponMapper {

    //增
    int insertCoupon(Coupon coupon);

    //userid获得Coupon列表
    List<Coupon> selectCouponByUser(int userId);

    //id获取
    Coupon selectById(int id);

    //增用户优惠券
    void insertCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);

    //删除用户优惠券
    void deleteCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);

    //金额小于amount的（可使用的）、属于用户的coupon
    List<Coupon> selectCouponByUserAndAmount(@Param("userId") int userId,@Param("amount") double amount);
}
