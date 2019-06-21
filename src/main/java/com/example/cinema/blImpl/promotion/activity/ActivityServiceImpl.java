package com.example.cinema.blImpl.promotion.activity;

import com.example.cinema.bl.promotion.ActivityService;
import com.example.cinema.blImpl.promotion.coupon.CouponServiceForBl;
import com.example.cinema.data.promotion.ActivityMapper;
import com.example.cinema.po.promotion.Activity;
import com.example.cinema.po.promotion.Coupon;
import com.example.cinema.vo.promotion.ActivityForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liying on 2019/4/20.
 */
@Service
public class ActivityServiceImpl implements ActivityService, ActivityServiceForBl {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    CouponServiceForBl couponService;

    /**
     * 发布活动和优惠券
     * @param activityForm
     * @return
     */
    @Override
    @Transactional
    public ResponseVO publishActivity(ActivityForm activityForm) {
        try {
            //将优惠券存入 activity数据库
            Coupon coupon = couponService.addCouponForBl(activityForm.getCouponForm());
            //将活动存入 activity数据库
            Activity activity = new Activity();
            activity.setName(activityForm.getName());
            activity.setDescription(activityForm.getDescription());
            activity.setStartTime(activityForm.getStartTime());
            activity.setEndTime(activityForm.getEndTime());
            activity.setCoupon(coupon);//活动对应的优惠券
            activityMapper.insertActivity(activity);
            //将活动和活动对应的电影存入 activity_movie数据库
            if(activityForm.getMovieList()!=null&&activityForm.getMovieList().size()!=0){
                activityMapper.insertActivityAndMovie(activity.getId(), activityForm.getMovieList());
            }
            return ResponseVO.buildSuccess(activityMapper.selectById(activity.getId()));
            //返回活动PO，此处有瑕疵，后端不应该传出PO对象？虽然在这里前端没有调用到
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 获取所有活动
     * @return
     */
    @Override
    public ResponseVO getActivities() {
        try {
            //Java8 新特性，类似python map[]
            //.stream().map(collection -> { return resultCollection;})
            //此处调用了Activity::getVO方法
            return ResponseVO.buildSuccess(activityMapper.selectActivities().stream().map(Activity::getVO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 通过activityId获取activityVO
     * @param id
     * @return
     */
    @Override
    public ResponseVO getActivityById(int id) {
        try {
            return ResponseVO.buildSuccess(activityMapper.selectById(id).getVO());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 【Bl】通过movieid获取活动列表
     * @param movieId
     * @return
     */
    @Override
    public List<Activity> selectActivitiesByMovie(int movieId) {
        try {
            return activityMapper.selectActivitiesByMovie(movieId);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
