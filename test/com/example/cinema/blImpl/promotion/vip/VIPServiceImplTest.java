package com.example.cinema.blImpl.promotion.vip;

import com.example.cinema.po.promotion.VIPCard;
import com.example.cinema.vo.promotion.VIPCardForm;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VIPServiceImplTest {

    @Autowired
    private VIPServiceImpl service;


    @Test
    public void charge() {
        VIPCardForm vipCardForm = new VIPCardForm();
        vipCardForm.setVipId(1);
        vipCardForm.setAmount(2000);
        service.charge(vipCardForm,1);
    }

    @Test
    public void addVIPCard() { //cardId 用于开户的银行卡
        service.addVIPCard(16,1);
    }

    @Test
    public void getCardById() {
        System.out.println(((VIPCard)service.getCardById(1).getContent()).getId());
    }

    @Test
    public void getCardByUserId() {
        System.out.println(((VIPCard)service.getCardByUserId(16).getContent()).getBalance());
    }

    @Test
    public void deleteCard() {
        service.deleteCard(8);

    }

    @Test
    public void deduct() {
        service.deduct(8,10);
    }

    @Test
    public void getCardByUserIdForBl() {
        service.getCardByUserIdForBl(16);
    }
}