package com.example.cinema.vo.promotion;

/**
 * @program: VIPTypeForm
 * @description: 会员卡种类信息（前端 到 后端）
 * @author: bbsngg
 * @create: 2019-06-05 20:55
 */
public class VIPTypeForm {


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


}
