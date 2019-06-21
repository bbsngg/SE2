package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.promotion.VIPCardForm;
import com.example.cinema.vo.ResponseVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/14.
 * Last update by SDJ on 2019/6/19
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;

    /**
     * 新增用户的会员卡
     * @param userId
     * @param cardId
     * @return VIPCard(Success) / String "失败"
     */
    @ApiOperation(value = "添加会员卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "账号id",dataType = "Integer"),
            @ApiImplicitParam(name = "cardId",value = "银行卡id",dataType = "Integer")
    })
    @PostMapping("/add")
    public ResponseVO addVIP(@RequestParam int userId,@RequestParam int cardId){
        return vipService.addVIPCard(userId,cardId);
    }

    /**
     * 通过用户id获取会员卡
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户的会员卡")
    @ApiImplicitParam(name = "userId",value = "账号id",dataType = "Integer")
    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId){
        return vipService.getCardByUserId(userId);
    }

    /**
     * 充值会员卡：1.充值 2.记录消费记录（卡内、log） 3.判断升级条件
     * @param vipCardForm
     * @return vipCard （前端判断是否升级）
     */
    @ApiOperation(value = "充值会员卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vipCardForm",value = "会员卡Form",dataType = "VIPCardForm"),
            @ApiImplicitParam(name = "cardId",value = "银行卡id",dataType = "Integer")
    })
    @PostMapping("/charge/{cardId}")
    public ResponseVO charge(@RequestBody VIPCardForm vipCardForm,@PathVariable int cardId){
        return vipService.charge(vipCardForm,cardId);
    }

    /**
     * 删除用户的会员卡
     * @param cardId
     * @return ResponseVO
     */
    @ApiOperation(value = "删除会员卡")
    @ApiImplicitParam(name = "cardId",value = "会员卡id",dataType = "Integer")
    @PostMapping("/delete")
    public ResponseVO deleteCard(@RequestParam int cardId) { return vipService.deleteCard(cardId); }
}
