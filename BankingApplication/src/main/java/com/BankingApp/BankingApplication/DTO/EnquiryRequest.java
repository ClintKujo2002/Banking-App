package com.BankingApp.BankingApplication.DTO;

public class EnquiryRequest {

    private String accountNumber;

    public EnquiryRequest(){}

    public EnquiryRequest(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
