package com.example.cinema.vo;

import io.swagger.models.auth.In;

public class BankCardForm {

    private Integer accountNumber;

    private Integer password;

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }
}
