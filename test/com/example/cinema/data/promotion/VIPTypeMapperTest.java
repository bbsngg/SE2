package com.example.cinema.data.promotion;

import com.example.cinema.po.promotion.VIPType;
import com.example.cinema.vo.promotion.VIPTypeForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VIPTypeMapperTest {

    @Autowired
    VIPTypeMapper mapper;

    @Test
    public void getAllType() {
    }

    @Test
    public void getDefaultPrice() {
    }

    @Test
    public void getDefaultDescription() {
    }

    @Test
    public void insertOneType() {

        VIPTypeForm f = new VIPTypeForm();
        f.setName("钻石卡");
        f.setTargetAmount(100.0);
        f.setDiscountAmount(30.0);
        f.setChargeAmount(2000.0);
        System.out.println(mapper.insertOneType(new VIPType(f)));
//        List<VIPType> l= mapper.getAllType();
//        System.out.println(l.get(l.size()-1).getId());
//        deleteType(l.get(l.size()-1).getId());
    }

    @Test
    public void selectTypeById() {

        VIPType it = mapper.selectTypeById(1);
        System.out.println(it.getId());
        System.out.println(it.getName());
        System.out.println();

    }

    @Test
    public void selectTypeByName() {
    }


    public void deleteType(int id) {
        mapper.deleteType(id);
    }

    @Test
    public void updateType() {
//        VIPTypeForm f = new VIPTypeForm();
//        f.setName("钻石卡");
//        f.setTargetAmount(100.0);
//        f.setDiscountAmount(30.0);
//        f.setChargeAmount(2000.0);
//        System.out.println(mapper.insertOneType(new VIPType(f)));

        List<VIPType> l= mapper.getAllType();
        int index = l.get(l.size()-1).getId();
        VIPType form = new VIPType();
        form.setId(index);
        form.setName("大便会员");
        form.setTargetAmount(1000);
        form.setDiscountAmount(1);
        form.setChargeAmount(100000);
        mapper.updateType(form);
    }

}