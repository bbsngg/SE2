package com.example.cinema.controller.router;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author deng
 * @date 2019/03/11
 */
@Api(description = "界面跳转api")
@Controller
//用于跳转到对应界面
public class ViewController {
    @RequestMapping(value = "/index")
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "/signUp")
    public String getSignUp() {
        return "signUp";
    }

    @RequestMapping(value = "/admin/movie/manage")
    public String getAdminMovieManage() {
        return "adminMovieManage";
    }

    @RequestMapping(value = "/admin/session/manage")
    public String getAdminSessionManage() {
        return "adminScheduleManage";
    }

    @RequestMapping(value = "/admin/cinema/manage")
    public String getAdminCinemaManage() {
        return "adminCinemaManage";
    }

    @RequestMapping(value = "/admin/promotion/manage")
    public String getAdminPromotionManage() {
        return "adminPromotionManage";
    }

    @RequestMapping(value = "/admin/cinema/statistic")
    public String getAdminCinemaStatistic() {
        return "adminCinemaStatistic";
    }

    @RequestMapping(value = "/admin/cinema/returnStrategy")
    public String getAdminReturnStrategy() {
        return "adminReturnStrategy";
    }

    @RequestMapping(value = "/admin/cinema/vipStrategy")
    public String getAdminVIPStrategy() {
        return "adminVIPStrategy";
    }

    @RequestMapping(value = "/admin/movieDetail")
    public String getAdminMovieDetail(@RequestParam int id) { return "adminMovieDetail"; }

    @RequestMapping(value = "/manager/authority")
    public String getManagerAuthority() { return "managerAuthority"; }

    @RequestMapping(value = "/user/home")
    public String getUserHome() {
        return "userHome";
    }

    @RequestMapping(value = "/user/buy")
    public String getUserBuy() {
        return "userBuy";
    }

    @RequestMapping(value = "/user/movieDetail")
    public String getUserMovieDetail(@RequestParam int id) {
        return "userMovieDetail";
    }

    @RequestMapping(value = "/user/movieDetail/buy")
    public String getUserMovieBuy(@RequestParam int id) {
        return "userMovieBuy";
    }

    @RequestMapping(value = "/user/movie")
    public String getUserMovie() {
        return "userMovie";
    }

    @RequestMapping(value = "/user/member")
    public String getUserMember() {
        return "userMember";
    }

    @RequestMapping(value = "/user/getTrans")
    public String getUserTrans(){return "userConsumeHistory";}

    @RequestMapping(value = "/user/getRecharge")
    public String getUserRecharge(){return "userRechargeHistory";}
}
