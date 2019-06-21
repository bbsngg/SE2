package com.example.cinema.controller.management;

import com.example.cinema.bl.management.ScheduleService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.ScheduleBatchDeleteForm;
import com.example.cinema.vo.ScheduleForm;
import com.example.cinema.vo.ScheduleViewForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**排片管理
 * @author fjj
 * @date 2019/4/11 4:13 PM
 */
@Api(description = "排片管理api")
@RestController
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    /**
     * 添加排片信息
     * @param scheduleForm
     * @return
     */
    @ApiOperation(value = "添加排片信息")
    @ApiImplicitParam(name = "scheduleForm",value = "排片Form",dataType = "ScheduleForm")
    @RequestMapping(value = "/schedule/add", method = RequestMethod.POST)
    public ResponseVO addSchedule(@RequestBody ScheduleForm scheduleForm){
        return scheduleService.addSchedule(scheduleForm);
    }

    /**
     * 更新排片信息
     * @param scheduleForm
     * @return
     */
    @ApiOperation(value = "更新排片信息")
    @ApiImplicitParam(name = "scheduleForm",value = "排片Form",dataType = "ScheduleForm")
    @RequestMapping(value = "/schedule/update", method = RequestMethod.POST)
    public ResponseVO updateSchedule(@RequestBody ScheduleForm scheduleForm){
        return scheduleService.updateSchedule(scheduleForm);
    }

    /**
     * 查找制定排片
     * @param hallId
     * @param startDate
     * @return
     */
    @ApiOperation(value = "查找指定排片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId",value = "影厅id",dataType = "Integer"),
            @ApiImplicitParam(name = "startDate",value = "开始日期",dataType = "Date")
    })
    @RequestMapping(value = "/schedule/search", method = RequestMethod.GET)
    public ResponseVO searchSchedule(@RequestParam int hallId, @RequestParam Date startDate){
        //这里传递startDate参数时，前端传的是用/分隔的时间，例如startDate=2019/04/12
        return scheduleService.searchScheduleSevenDays(hallId, startDate);
    }

    /**
     * 根据电影id返回观众看到的排片信息
     * @param movieId
     * @return
     */
    @ApiOperation(value = "根据电影id返回观众看到的排片信息")
    @ApiImplicitParam(name = "movieId",value = "电影id",dataType = "Integer")
    @RequestMapping(value = "/schedule/search/audience", method = RequestMethod.GET)
    public ResponseVO searchAudienceSchedule(@RequestParam int movieId){
        return scheduleService.searchAudienceSchedule(movieId);
    }

    /**
     * 设置排片对观众可见天数
     * @param scheduleViewForm
     * @return
     */
    @ApiOperation(value = "设置排片对观众可见天数")
    @ApiImplicitParam(name = "scheduleViewForm",value = "可见天数Form",dataType = "ScheduleViewForm")
    @RequestMapping(value = "/schedule/view/set", method = RequestMethod.POST)
    public ResponseVO setScheduleView(@RequestBody  ScheduleViewForm scheduleViewForm){
        return scheduleService.setScheduleView(scheduleViewForm);
    }

    /**
     * 查询排片对观众可见天数
     * @return
     */
    @ApiOperation(value = "查询排片对观众可见天数")
    @RequestMapping(value = "/schedule/view", method = RequestMethod.GET)
    public ResponseVO getScheduleView(){
        return scheduleService.getScheduleView();
    }

    /**
     * 删除一系列排片
     * @param scheduleBatchDeleteForm
     * @return
     */
    @ApiOperation(value = "删除一系列排片")
    @ApiImplicitParam(name = "scheduleBatchDeleteForm",value = "排片id的list",dataType = "ScheduleBatchDeleteForm")
    @RequestMapping(value = "/schedule/delete/batch", method = RequestMethod.DELETE)
    public ResponseVO deleteBatchOfSchedule(@RequestBody ScheduleBatchDeleteForm scheduleBatchDeleteForm){
        return scheduleService.deleteBatchOfSchedule(scheduleBatchDeleteForm);
    }

    /**
     * 根据id查找排片
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查找排片")
    @ApiImplicitParam(name = "id",value = "排片id",dataType = "Integer")
    @RequestMapping(value = "/schedule/{id}", method = RequestMethod.GET)
    public ResponseVO getScheduleById(@PathVariable int id){
        return scheduleService.getScheduleById(id);
    }
}
