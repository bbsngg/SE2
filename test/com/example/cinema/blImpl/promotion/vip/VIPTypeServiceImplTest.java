package com.example.cinema.blImpl.promotion.vip;

import com.example.cinema.data.promotion.VIPTypeMapper;
import com.example.cinema.vo.promotion.VIPTypeForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VIPTypeServiceImplTest {

    @Autowired
    private VIPTypeServiceImpl service;

    @Autowired
    private VIPTypeMapper mapper;

    @Test
    public void getAllType() {
//        List<VIPTypeForm> list = new ArrayList<VIPTypeForm>();
        List<Object> list= (List<Object>) service.getAllType().getContent();
        list.forEach(item -> {
            VIPTypeForm it = (VIPTypeForm)item;
            System.out.println(it.getId());
            System.out.println(it.getName());
            System.out.println();
        });

    }

    @Test
    public void addType() {
        VIPTypeForm f = new VIPTypeForm();
        f.setName("钻石卡");
        f.setTargetAmount(100.0);
        f.setDiscountAmount(30.0);
        f.setChargeAmount(2000.0);
        service.addType(f);
    }

    @Test
    public void updateType() {
        VIPTypeForm f = new VIPTypeForm();
        List<VIPTypeForm> l= (List<VIPTypeForm>)service.getAllType().getContent();
        int lastid = l.get(l.size()-1).getId();
        f.setId(lastid);
        f.setName("王者卡");
        f.setTargetAmount(40.0);
        f.setDiscountAmount(30.0);
        f.setChargeAmount(222000.0);
        System.out.println(service.updateType(f).getContent());
    }

    @Test
    public void delLast(){
        List<VIPTypeForm> l= (List<VIPTypeForm>)service.getAllType().getContent();
        System.out.println(l.get(l.size()-1).getId());
        System.out.println();
        delTypeById(l.get(l.size()-1).getId());
    }

    public void delTypeById(int id) {

        service.delTypeById(id);
    }


    @Test
    public void getVIPInfo() {

    }
}