package com.example.cinema.data.promotion;

import com.example.cinema.po.promotion.VIPType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by bbsngg on 2019/6/5.
 */
@Mapper
public interface VIPTypeMapper {

    List<VIPType> getAllType();

//    double getDefaultPrice();

//    String getDefaultDescription();

    int insertOneType(VIPType vipType);

    VIPType selectTypeById(int id);

    VIPType selectTypeByName(String name);

    void deleteType(int id);

    void updateType(VIPType vipType);

}
