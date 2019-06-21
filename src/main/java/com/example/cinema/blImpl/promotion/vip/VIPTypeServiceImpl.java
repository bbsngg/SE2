package com.example.cinema.blImpl.promotion.vip;

import com.example.cinema.bl.promotion.VIPTypeService;
import com.example.cinema.data.promotion.VIPTypeMapper;
import com.example.cinema.po.promotion.VIPType;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.promotion.VIPInfoVO;
import com.example.cinema.vo.promotion.VIPTypeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: VIPTypeServiceImpl
 * @description: 关于会员卡种类的操作实现
 * @author: bbsngg
 * @create: 2019-06-14 16:02
 */
@Service
public class VIPTypeServiceImpl implements VIPTypeService {

// 会员卡种类相关
    @Autowired
    VIPTypeMapper vipTypeMapper;



    /**
     * 新增一个会员卡种类
     * @param vipTypeForm
     * @return
     */
    @Override
    public ResponseVO addType(VIPTypeForm vipTypeForm) {
        vipTypeMapper.insertOneType(new VIPType(vipTypeForm));
        return ResponseVO.buildSuccess("成功");
    }

    /**
     * 获取所有会员卡种类
     * @return
     */
    @Override
    public ResponseVO getAllType() {
        List<VIPType> list =  vipTypeMapper.getAllType();
        List<VIPTypeForm> retList = new ArrayList<>();
        list.forEach(item -> {retList.add(item.toForm());});
        return ResponseVO.buildSuccess(retList);
    }

    /**
     * 通过id，获取会员卡信息
     * @return ResponseVO.buildSuccess(vipInfoVO)
     */
    @Override
    public ResponseVO getVIPInfo(int typeId) {
        VIPInfoVO vipInfoVO = new VIPInfoVO();
        VIPType vipType = vipTypeMapper.selectTypeById(typeId);
        String desc = "满"+String.valueOf((int)vipType.getTargetAmount())+"送"+String.valueOf((int)vipType.getDiscountAmount());
        double chargeAmount = vipType.getChargeAmount();

        vipInfoVO.setDescription(desc);
        vipInfoVO.setPrice(chargeAmount);
        return ResponseVO.buildSuccess(vipInfoVO);
    }

    /**
     * 更新某会员卡种类的信息（通过id）
     * @param vipTypeForm
     * @return
     */
    @Override
    public ResponseVO updateType(VIPTypeForm vipTypeForm) {
//        (int id, String name, double targetAmount, double discountAmount, double chargeAmount)
        VIPType vipType = new VIPType(vipTypeForm);
        vipTypeMapper.updateType(vipType);
        return ResponseVO.buildSuccess("成功");
    }

    /**
     * 通过种类id删除种类
     * @param typeId
     * @return
     */
    @Override
    public ResponseVO delTypeById(int typeId) {
        vipTypeMapper.deleteType(typeId);
        return ResponseVO.buildSuccess("成功");
    }

}
