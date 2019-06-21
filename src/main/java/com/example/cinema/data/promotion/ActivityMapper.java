package com.example.cinema.data.promotion;

import com.example.cinema.po.promotion.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/20.
 */
@Mapper
public interface ActivityMapper {

    //增
    int insertActivity(Activity activity);

    //增加活动与电影（关联）
    int insertActivityAndMovie(@Param("activityId") int activityId,@Param("movieId") List<Integer> movieId);

    //获取所有
    List<Activity> selectActivities();

    //根据id获得对应未过期的活动列表，mysql内置函数now()实现
    List<Activity> selectActivitiesByMovie(int movieId);

    //id获取
    Activity selectById(int id);

    List<Activity> selectActivitiesWithoutMovie();

}
