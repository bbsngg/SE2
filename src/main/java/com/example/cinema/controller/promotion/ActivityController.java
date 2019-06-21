package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.ActivityService;
import com.example.cinema.vo.promotion.ActivityForm;
import com.example.cinema.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by liying on 2019/4/20.
 *
 */
@Api(description = "活动管理api")
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    /**
     * 发布一项活动
     * @param activityForm
     * @return
     */
    @ApiOperation(value = "发布活动")
    @ApiImplicitParam(name = "activityForm",value = "活动Form",dataType = "ActivityForm")
    @PostMapping("/publish")
    public ResponseVO publishActivity(@RequestBody ActivityForm activityForm){
        return activityService.publishActivity(activityForm);
    }

    /**
     * 获取所有的活动
     * @return
     */
    @ApiOperation(value = "获取所有活动")
    @GetMapping("/get")
    public ResponseVO getActivities(){
        return activityService.getActivities();
    }

}
