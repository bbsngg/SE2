package com.example.cinema.blImpl.promotion.vip;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.blImpl.logs.TransactionLogServiceForBl;
import com.example.cinema.blImpl.user.BankAccountServiceForBl;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.promotion.VIPTypeMapper;
import com.example.cinema.po.BankCard;
import com.example.cinema.po.TransactionLog;
import com.example.cinema.po.promotion.VIPType;
import com.example.cinema.vo.promotion.VIPCardForm;
import com.example.cinema.po.promotion.VIPCard;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService, VIPServiceForBl {
    @Autowired
    VIPCardMapper vipCardMapper;

    @Autowired
    VIPTypeMapper vipTypeMapper;

    @Autowired
    TransactionLogServiceForBl transactionLogServiceForBl;

    @Autowired
    BankAccountServiceForBl bankAccountService;

// VIPServiceForBl

// 会员卡--用户相关
    /**
     * 增加用户的VIP卡 1.新增一张用户会员卡 2.新增一条消费记录
     * @param userId
     * @return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id))
     */
    @Override
    public ResponseVO addVIPCard(int userId,int cardId) {
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setCardType(1);
        vipCard.setBalance(0);
        vipCard.setAmount(0);
        try {
            //新增一张会员卡
            int id = vipCardMapper.insertOneCard(vipCard);
            //修改银行卡余额
            BankCard bankCard=bankAccountService.getBankCardById(cardId);
            double total=vipTypeMapper.getAllType().get(0).getChargeAmount();
            double balance=bankCard.getBalance();
            if(total>balance)
                return ResponseVO.buildFailure("银行卡余额不足");
            bankAccountService.deduct(cardId,total);
            //新增消费记录
            transactionLogServiceForBl.addTran(new TransactionLog
                    (total,userId,"-1",bankCard.getAccountNumber()+""));

            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 通过卡id获取VIP卡
     * @param id
     * @return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
     */
    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 会员卡充值
     * @param vipCardForm
     * @return
     */
    @Override
    public ResponseVO charge(VIPCardForm vipCardForm,int cardId) {
        
        VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }

        try {
            VIPType type = vipTypeMapper.selectTypeById(vipCard.getCardType());
            double chargeAmount = vipCardForm.getAmount();
            double balance = vipCard.calculate(chargeAmount,
                    type.getTargetAmount(),type.getDiscountAmount());//实际充值额度
            //银行卡扣费
            BankCard bankCard=bankAccountService.getBankCardById(cardId);
            double bankCardBalance=bankCard.getBalance();
            if(chargeAmount>bankCardBalance)
                return ResponseVO.buildFailure("余额不足");
            bankAccountService.deduct(cardId,chargeAmount);

            //保存充值记录（卡内）
            vipCard.setAmount(vipCard.getAmount() + chargeAmount);//缓存
            vipCardMapper.updateCardAmount(vipCardForm.getVipId(),vipCard.getAmount());
            //保存消费记录
            transactionLogServiceForBl.addTran(new TransactionLog
                    (chargeAmount,vipCard.getUserId(),"0",bankCard.getAccountNumber()+""));
            //设置卡的余额
            vipCard.setBalance(vipCard.getBalance() + balance);
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCard.getBalance());//设置卡余额
            //检查并执行卡升级
            VIPCard newCard = checkUpdateCardType(vipCard);

            return ResponseVO.buildSuccess(newCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 通过用户id获取VIP卡
     * @param userId
     * @return ResponseVO
     */
    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 删除用户的会员卡
     * @param cardId
     * @return ResponseVO
     */
    @Override
    public ResponseVO deleteCard(int cardId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardById(cardId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            vipCardMapper.delCard(cardId);
            return ResponseVO.buildSuccess("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

//会员卡种类相关

    /**
     * 【Private】会员卡自动升级
     * @param vipCard
     * @return
     */
    private VIPCard checkUpdateCardType(VIPCard vipCard){
        List<VIPType> typeList = vipTypeMapper.getAllType();
        for (VIPType type : typeList){

            if (type.getId() < vipCard.getCardType()) //默认高级卡 id较大
                continue;

            if(typeList.indexOf(type)!=typeList.size()-1
                    && vipCard.getAmount() >=
                    typeList.get(typeList.indexOf(type)+1).getChargeAmount()) //充值额度大于此类型的chargeAmount
                continue;
            else{
                int typeId = type.getId();
                vipCard.setCardType(typeId);
                vipCardMapper.updateCardType(vipCard.getId(),typeId);
                break;
            }
        }

        return vipCardMapper.selectCardById(vipCard.getId());
    }


// VIPServiceForBl

    /**
     * 【Bl】会员卡扣费
     * @param vipId
     * @param fare
     * @return
     */
    @Override
    public String deduct(int vipId, double fare) {
        VIPCard vipCard = vipCardMapper.selectCardById(vipId);
        if (vipCard == null) {
            return "会员卡不存在";
        }
        vipCard.setBalance(vipCard.getBalance() - fare);
        try {
            vipCardMapper.updateCardBalance(vipId, vipCard.getBalance());
            return "成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }
    }

    /**
     * 【Bl】通过用户id获取VIP卡
     * @param userId
     * @return VIPCard
     */
    @Override
    public VIPCard getCardByUserIdForBl(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null)
                throw new Exception("用户卡不存在");
            return vipCard;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
