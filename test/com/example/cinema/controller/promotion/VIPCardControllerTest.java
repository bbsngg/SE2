package com.example.cinema.controller.promotion;

import com.example.cinema.vo.promotion.VIPCardForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VIPCardControllerTest {

    @Autowired
    VIPCardController c;
    @Test
    public void charge() {
        VIPCardForm vipCardForm = new VIPCardForm();
        vipCardForm.setVipId(2);
        vipCardForm.setAmount(2000);

        c.charge(vipCardForm, 1);
    }
}