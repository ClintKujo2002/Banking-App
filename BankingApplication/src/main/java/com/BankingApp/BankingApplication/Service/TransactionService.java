package com.BankingApp.BankingApplication.Service;

import com.BankingApp.BankingApplication.DTO.TransactionDTO;
import com.BankingApp.BankingApplication.Entity.Transaction;
import com.BankingApp.BankingApplication.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements TransactionServiceInterface{

    @Autowired
    TransactionRepository transactionRepository;


    @Override
    public void saveTransaction(TransactionDTO transactionDTO) {

        Transaction transaction = new Transaction();
        transaction.getTransactionType();
        transaction.getAccountNumber();
        transaction.getAmount();
        transaction.setStatus("SUCCESS");
        transactionRepository.save(transaction);

    }
}
