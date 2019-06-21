package com.example.cinema.blImpl.promotion.activity;

import com.example.cinema.po.promotion.Activity;

import java.util.List;

public interface ActivityServiceForBl {

    //通过movieid获取活动列表
    List<Activity> selectActivitiesByMovie(int movieId);

}
