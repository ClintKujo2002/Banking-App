package com.BankingApp.BankingApplication.Repository;

import com.BankingApp.BankingApplication.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
