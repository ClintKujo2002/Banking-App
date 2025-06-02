package com.BankingApp.BankingApplication.DTO;

import lombok.*;

import java.math.BigDecimal;


public class AccountInfo {

    private String accountName;

    private BigDecimal accountBalance;

    private String accountNumber;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public  AccountInfo () {}

    public AccountInfo(String accountName, BigDecimal accountBalance, String accountNumber) {
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        this.accountNumber = accountNumber;
    }
}
