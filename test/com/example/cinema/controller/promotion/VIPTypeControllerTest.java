package com.example.cinema.controller.promotion;

import com.example.cinema.vo.promotion.VIPTypeForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class VIPTypeControllerTest {

    @Autowired
    VIPTypeController c;

    @Test
    public void getAllType() {
    }

    @Test
    public void addType() {
    }

    @Test
    public void updateType() {

        VIPTypeForm form = new VIPTypeForm();
        form.setId(2);
        form.setName("大便会员");
        form.setTargetAmount(1000);
        form.setDiscountAmount(1);
        form.setChargeAmount(100000);
        c.updateType(form);

    }

    @Test
    public void delTypeById() {
    }
}