package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.BankAccountService;
import com.example.cinema.data.user.BankAccountMapper;
import com.example.cinema.po.BankCard;
import com.example.cinema.vo.BankCardForm;
import com.example.cinema.vo.BankCardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccoutServiceImpl implements BankAccountService,BankAccountServiceForBl {
    @Autowired
    BankAccountMapper bankAccountMapper;

    @Override
    public BankCardVO login(BankCardForm bankCardForm) {
        BankCard bankCard=bankAccountMapper.getAccountByName(bankCardForm.getAccountNumber());
        if(null == bankCard || !bankCard.getPassword().equals(bankCardForm.getPassword())){
            return null;
        }
        return new BankCardVO(bankCard);
    }

    /**
     * 修改银行卡余额，注意fare是扣除金额
     * @param bankCardId
     * @param fare
     * @return
     */
    @Override
    public String deduct(int bankCardId, double fare) {
        BankCard bankCard=bankAccountMapper.getAccountById(bankCardId);
        if (bankCard == null) {
            return "会员卡不存在";
        }
        bankCard.setBalance(bankCard.getBalance() - fare);
        try {
            bankAccountMapper.updateCardBalance(bankCardId, bankCard.getBalance());
            return "成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }
    }

    @Override
    public BankCard getBankCardById(int id) {
        try{
            BankCard bankCard=bankAccountMapper.getAccountById(id);
            if(bankCard==null)
                throw new Exception("银行卡不存在");
            return bankCard;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BankCard getBankCardByAccountName(int accountNumber) {
        try{
            BankCard bankCard=bankAccountMapper.getAccountByName(accountNumber);
            if(bankCard==null)
                throw new Exception("银行卡不存在");
            return bankCard;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
