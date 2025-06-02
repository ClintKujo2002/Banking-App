package com.BankingApp.BankingApplication.Service;

import com.BankingApp.BankingApplication.DTO.TransactionDTO;
import com.BankingApp.BankingApplication.Entity.Transaction;

public interface TransactionServiceInterface {
    void saveTransaction(TransactionDTO transactionDTO);
}
