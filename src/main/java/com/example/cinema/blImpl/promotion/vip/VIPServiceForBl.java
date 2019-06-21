package com.example.cinema.blImpl.promotion.vip;

import com.example.cinema.po.promotion.VIPCard;

/**
 * @author sdj
 * @data 2019/5/15 21:02 PM
 */
public interface VIPServiceForBl {

//    ResponseVO addVIPCard(int userId);

//    ResponseVO getCardById(int id);

//    ResponseVO getVIPInfo();

//    ResponseVO charge(VIPCardForm vipCardForm);

    //会员卡扣费
    String deduct(int vipId, double fare);//根据vipId修改余额

    //通过用户id获取VIP卡
    VIPCard getCardByUserIdForBl(int userId);

}
