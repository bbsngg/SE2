package com.example.cinema.po;

import java.sql.Timestamp;

public class TransactionLog {
    private Integer id;

    private Integer userId;

    private Double amount;

    private String method;

    private String uses;

    private Timestamp time;

    public TransactionLog(){
        //normal
    }

    public TransactionLog(double amount,int userId,String uses,String method) {
        this.setAmount(amount);
        this.setUserId(userId);
        this.setUses(uses);
        this.setMethod(method);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }

    public Timestamp getTime(){
        return time;
    }

    public void setTime(Timestamp time){
        this.time=time;
    }
}
