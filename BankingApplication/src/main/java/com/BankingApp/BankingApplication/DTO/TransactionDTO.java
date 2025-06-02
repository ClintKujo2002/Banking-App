package com.BankingApp.BankingApplication.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {

    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String status;
    private LocalDateTime createdAt;

    public TransactionDTO() {
    }

    public TransactionDTO(String transactionType, BigDecimal amount, String accountNumber, String status, LocalDateTime createdAt) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}