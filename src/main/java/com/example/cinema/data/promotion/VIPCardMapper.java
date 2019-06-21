package com.example.cinema.data.promotion;

import com.example.cinema.po.promotion.VIPCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/14.
 */
@Mapper
public interface VIPCardMapper {

    //增
    int insertOneCard(VIPCard vipCard);

    VIPCard selectCardById(int id);

    //更新余额
    void updateCardBalance(@Param("id") int id,@Param("balance") double balance);

    VIPCard selectCardByUserId(int userId);

    List<VIPCard> selectCardByType(int cardType);

    //卡升级
    void updateCardType(@Param("id") int id,@Param("cardType") int cardType);

    //消费充值记录
    void updateCardAmount(@Param("id") int id,@Param("amount") double amount);

    void delCard(@Param("id") int id);
}
