package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.vo.ResponseVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * Created by liying on 2019/4/16.
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    /**
     * 获取用户的优惠券
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户持有的优惠券")
    @ApiImplicitParam(name = "userId",value = "账号id",dataType = "Integer")
    @GetMapping("{userId}/get")
    public ResponseVO getCoupons(@PathVariable int userId){
        return couponService.getCouponsByUser(userId);
    }

    /**
     * 管理员按条件查询消费达到一定金额的会员名单，并赠送优惠券
     * @param couponId
     * @param userId
     * @return
     */
    @ApiOperation(value = "赠送用户指定优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "couponId",value = "优惠券id",dataType = "Integer"),
            @ApiImplicitParam(name = "userId",value = "账号id",dataType = "Integer")
    })

    @PostMapping("/giveByUserId")
    public ResponseVO giveByUserId(@PathParam("couponId") int couponId, @PathParam("userId") int userId) {
        return couponService.issueCoupon(couponId, userId);
    }


}
