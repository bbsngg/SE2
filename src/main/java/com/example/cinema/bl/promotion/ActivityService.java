package com.example.cinema.bl.promotion;

import com.example.cinema.vo.promotion.ActivityForm;
import com.example.cinema.vo.ResponseVO;

/**
 * Created by liying on 2019/4/20.
 */
public interface ActivityService {

    //发布活动和优惠券
    ResponseVO publishActivity(ActivityForm activityForm);

    //获取所有活动
    ResponseVO getActivities();

    //通过activityId获取activityVO
    ResponseVO getActivityById(int id);


}
