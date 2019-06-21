package com.example.cinema.vo;

import com.example.cinema.po.BankCard;

public class BankCardVO {
    private Integer id;
    private Integer amountNumber;
    private Integer password;
    private Double balance;

    public BankCardVO(BankCard bankCard){
        this.id=bankCard.getId();
        this.amountNumber=bankCard.getAccountNumber();
        this.password=bankCard.getPassword();
        this.balance=bankCard.getBalance();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmountNumber() {
        return amountNumber;
    }

    public void setAmountNumber(Integer amountNumber) {
        this.amountNumber = amountNumber;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
