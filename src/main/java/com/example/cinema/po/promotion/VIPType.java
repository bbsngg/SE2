package com.example.cinema.po.promotion;

import com.example.cinema.vo.promotion.VIPTypeForm;

/**
 * @program: VIPType
 * @description: 会员卡种类
 * id	name
 * target_amount目标价
 * discount_amount打折价
 * charge_amount到达升级金额
 *
 * @author: bbsngg
 * @create: 2019-06-05 21:20
 */
public class VIPType {

    /**
     * 会员卡种类id
     */
    private int id;

    /**
     * 会员卡种类名称
     */
    private String name;

    /**
     * 目标价格
     */
    private double targetAmount;

    /**
     * 打折价格
     */
    private double discountAmount;

    /**
     * 到达升级金额
     */
    private double chargeAmount;

    public VIPType(){

    }

    public VIPType(VIPTypeForm f){
        this.setId(f.getId());
        this.setChargeAmount(f.getChargeAmount());
        this.setDiscountAmount(f.getDiscountAmount());
        this.setName(f.getName());
        this.setTargetAmount(f.getTargetAmount());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    //ToForm
    public VIPTypeForm toForm(){
        VIPTypeForm r = new VIPTypeForm();
        r.setChargeAmount(chargeAmount);
        r.setDiscountAmount(discountAmount);
        r.setId(id);
        r.setName(name);
        r.setTargetAmount(targetAmount);
        return r;
    }
}
