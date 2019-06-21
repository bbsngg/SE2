package com.example.cinema.controller.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketForm;
import com.example.cinema.vo.TicketWithCouponForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Api(description = "票务api")
@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    /**
     * 会员购票
     * @param ticketWithCouponForm
     * @return
     */
    @ApiOperation(value = "会员购票")
    @ApiImplicitParam(name = "ticketWithCouponForm",value = "电影票&优惠券Form",dataType = "TicketWithCouponForm")
    @PostMapping("/vip/buy")
    public ResponseVO buyTicketByVIPCard(@RequestBody TicketWithCouponForm ticketWithCouponForm){
        return ticketService.completeByVIPCard(ticketWithCouponForm.getTicketId(),ticketWithCouponForm.getCouponId());
    }

    /**
     * 用户锁座
     * @param ticketForm
     * @return
     */
    @ApiOperation(value = "锁座")
    @ApiImplicitParam(name = "ticketForm",value = "电影票Form",dataType = "TicketForm")
    @PostMapping("/lockSeat")
    public ResponseVO lockSeat(@RequestBody TicketForm ticketForm){
        return ticketService.addTicket(ticketForm);
    }

    /**
     * 普通用户购票
     * @param ticketWithCouponForm
     * @param cardId
     * @return
     */
    @ApiOperation(value = "银行卡购票")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ticketWithCouponForm",value = "电影票&优惠券Form",dataType = "TicketWithCouponForm"),
            @ApiImplicitParam(name = "cardId",value = "银行卡id",dataType = "Integer")
    })
    @PostMapping("/buy/{cardId}")
    public ResponseVO buyTicket(@RequestBody TicketWithCouponForm ticketWithCouponForm,@PathVariable int cardId){
        return ticketService.completeTicket(ticketWithCouponForm.getTicketId(),ticketWithCouponForm.getCouponId(),cardId);
    }

    /**
     * 通过用户id获取用户的订票
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据获得用户持有的电影票")
    @ApiImplicitParam(name = "userId",value = "账号id",dataType = "Integer")
    @GetMapping("/get/{userId}")
    public ResponseVO getTicketByUserId(@PathVariable int userId){
        return ticketService.getTicketByUser(userId);
    }

    /**
     * 通过安排id获取座位
     * @param scheduleId
     * @return
     */
    @ApiOperation(value = "获取某场已购买的座位")
    @ApiImplicitParam(name = "scheduleId",value = "排片id",dataType = "Integer")
    @GetMapping("/get/occupiedSeats")
    public ResponseVO getOccupiedSeats(@RequestParam int scheduleId){
        return ticketService.getBySchedule(scheduleId);
    }

    /**
     * 取消锁座
     * @param ticketWithCouponForm
     * @return
     */
    @ApiOperation(value = "取消购票")
    @ApiImplicitParam(name = "ticketWithCouponForm",value = "电影票&优惠券Form",dataType = "TicketWithCouponForm")
    @PostMapping("/cancel")
    public ResponseVO cancelTicket(@RequestBody TicketWithCouponForm ticketWithCouponForm){
        return ticketService.cancelTicket(ticketWithCouponForm.getTicketId());
    }

    /**
     * 用户退票
     * @param ticketId
     * @return
     */
    @ApiOperation(value = "退票")
    @ApiImplicitParam(name = "ticketId",value = "电影票id",dataType = "Integer")
    @PostMapping("/refund")
    public ResponseVO refundTicket(@RequestParam int ticketId){
        return ticketService.refundTicket(ticketId);
    }

    /**
     *
     * @param idList
     * @return
     */
    @ApiOperation(value = "获取消费详情")
    @ApiImplicitParam(name = "idList",value = "电影票list",dataType = "String")
    @GetMapping("/get/details/{idList}")
    public ResponseVO getDetails(@PathVariable String idList){
        return ticketService.getDetailsByTicketId(idList);
    }

    //@RequestParam获取url后面？key1=value1&key2=value2...的某个参数
    //@RequestBody获取ajax传来的一个data，一般是一个对象


}
