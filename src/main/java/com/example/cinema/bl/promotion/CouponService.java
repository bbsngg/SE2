package com.example.cinema.bl.promotion;

import com.example.cinema.vo.promotion.CouponForm;
import com.example.cinema.vo.ResponseVO;

/**
 * Created by liying on 2019/4/17.
 */
public interface CouponService {

    //获取用户优惠券
    ResponseVO getCouponsByUser(int userId);

    //新增优惠券
    ResponseVO addCoupon(CouponForm couponForm);

    //增加用户的优惠券
    ResponseVO issueCoupon(int couponId,int userId);

}
