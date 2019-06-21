package com.example.cinema.controller.statistics;

import com.example.cinema.bl.statistics.StatisticsService;
import com.example.cinema.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fjj
 * @date 2019/4/16 1:34 PM
 */
@Api(description = "统计api")
@RestController
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @ApiOperation(value = "获取指定日期的排片")
    @ApiImplicitParam(name = "date",value = "日期",dataType = "Date",required = false)
    @RequestMapping(value = "statistics/scheduleRate", method = RequestMethod.GET)
    public ResponseVO getScheduleRateByDate(@RequestParam(required = false) Date date){
        //此处date为非必填参数，若不填则默认为当天
        return statisticsService.getScheduleRateByDate(date);
    }

    @ApiOperation(value = "获取所有电影累计票房")
    @RequestMapping(value = "statistics/boxOffice/total", method = RequestMethod.GET)
    public ResponseVO getTotalBoxOffice(){
        return statisticsService.getTotalBoxOffice();
    }

    @ApiOperation(value = "获取过去7天的客单价")
    @RequestMapping(value = "statistics/audience/price", method = RequestMethod.GET)
    public ResponseVO getAudiencePrice(){
        return statisticsService.getAudiencePriceSevenDays();
    }

    @ApiOperation(value = "获取所有电影指定日期的上座率")
    @ApiImplicitParam(name = "date",value = "日期",dataType = "Date")
    @RequestMapping(value = "statistics/PlacingRate", method = RequestMethod.GET)
    public ResponseVO getMoviePlacingRateByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date date){
//        System.out.println("date:"+date);
        try{
            String dateString = "2019-04-23 00:00:00";
            Date date1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
            return statisticsService.getMoviePlacingRateByDate(date1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "获取最近给定天数最受欢迎的电影")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "days",value = "距今天数",dataType = "Integer"),
            @ApiImplicitParam(name = "movieNum",value = "展示电影数",dataType = "Integer")
    })
    @RequestMapping(value = "statistics/popular/movie", method = RequestMethod.GET)
    public ResponseVO getPopularMovies(@RequestParam int days, @RequestParam int movieNum){
        return statisticsService.getPopularMovies(days, movieNum);
    }










}
