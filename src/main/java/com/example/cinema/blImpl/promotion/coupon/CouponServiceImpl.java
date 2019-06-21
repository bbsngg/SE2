package com.example.cinema.blImpl.promotion.coupon;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.po.promotion.Coupon;
import com.example.cinema.vo.promotion.CouponForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liying on 2019/4/17.
 */
@Service
public class CouponServiceImpl implements CouponService, CouponServiceForBl {

    @Autowired
    CouponMapper couponMapper;

    /**
     * 获取用户的所有优惠券
     * @param userId
     * @return
     */
    @Override
    public ResponseVO getCouponsByUser(int userId) {
        try {
            return ResponseVO.buildSuccess(couponMapper.selectCouponByUser(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 增加新的优惠券
     * @param couponForm
     * @return
     */
    @Override
    public ResponseVO addCoupon(CouponForm couponForm) {
        try {
            //设置新的优惠券并存入 coupon数据库
            Coupon coupon=new Coupon();
            coupon.setName(couponForm.getName());
            coupon.setDescription(couponForm.getDescription());
            coupon.setTargetAmount(couponForm.getTargetAmount());
            coupon.setDiscountAmount(couponForm.getDiscountAmount());
            coupon.setStartTime(couponForm.getStartTime());
            coupon.setEndTime(couponForm.getEndTime());
            couponMapper.insertCoupon(coupon);

            return ResponseVO.buildSuccess(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 增加用户的优惠券
     * @param couponId
     * @param userId
     * @return
     */
    @Override
    public ResponseVO issueCoupon(int couponId, int userId) {
        try {
            couponMapper.insertCouponUser(couponId,userId);
            return ResponseVO.buildSuccess("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

//BL

    /**
     * 【Bl】通过userid获取优惠券
     * @param userId
     * @return
     */
    @Override
    public List<Coupon> selectCouponByUser(int userId) {
        try {
            return couponMapper.selectCouponByUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 【Bl】增加userid用户的couponid优惠券
     * @param couponId
     * @param userId
     * @return
     */
    @Override
    public String insertCouponUser(int couponId, int userId) {
        try {
            couponMapper.insertCouponUser(couponId, userId);
            return "成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }
    }

    /**
     * 【Bl】删除userid用户的couponid优惠券
     * @param couponId
     * @param userId
     * @return
     */
    @Override
    public String deleteCouponUser(int couponId, int userId) {
        try {
            couponMapper.deleteCouponUser(couponId, userId);
            return "成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }
    }

    /**
     * 【Bl】新增优惠券
     * @param couponForm
     * @return
     */
    @Override
    public Coupon addCouponForBl(CouponForm couponForm) {
        try {
            //设置新的优惠券并存入 coupon数据库
            Coupon coupon=new Coupon();
            coupon.setName(couponForm.getName());
            coupon.setDescription(couponForm.getDescription());
            coupon.setTargetAmount(couponForm.getTargetAmount());
            coupon.setDiscountAmount(couponForm.getDiscountAmount());
            coupon.setStartTime(couponForm.getStartTime());
            coupon.setEndTime(couponForm.getEndTime());
            couponMapper.insertCoupon(coupon);

            return coupon;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 【Bl】couponid获取优惠券
     * @param id
     * @return
     */
    @Override
    public Coupon selectById(int id) {
        try {
            return couponMapper.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
