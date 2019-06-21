package com.example.cinema.bl.promotion;

import com.example.cinema.vo.promotion.VIPCardForm;
import com.example.cinema.vo.ResponseVO;


/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    /**
     * 会员卡-用户相关
     */

    //增加用户的VIP卡 1.新增一张用户会员卡 2.新增一条消费记录
    ResponseVO addVIPCard(int userId,int cardId);

    //通过卡id获取VIP卡
    ResponseVO getCardById(int id);

    //会员卡充值
    ResponseVO charge(VIPCardForm vipCardForm,int cardId);

    //通过用户id获取VIP卡
    ResponseVO getCardByUserId(int userId);

    //删除用户的会员卡
    ResponseVO deleteCard(int cardId);

}
