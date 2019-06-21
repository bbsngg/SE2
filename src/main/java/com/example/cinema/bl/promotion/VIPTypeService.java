package com.example.cinema.bl.promotion;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.promotion.VIPTypeForm;

public interface VIPTypeService {


    /**
     * 会员卡类型相关
     */

    //新增一个会员卡种类
    ResponseVO addType(VIPTypeForm vipTypeForm);

    //获取所有会员卡种类
    ResponseVO getAllType();

    //通过id，获取会员卡信息
    ResponseVO getVIPInfo(int typeId);

    //更新某会员卡种类的信息（通过id）
    ResponseVO updateType(VIPTypeForm vipTypeForm);

    //通过种类id删除种类
    ResponseVO delTypeById(int typeId);
}
