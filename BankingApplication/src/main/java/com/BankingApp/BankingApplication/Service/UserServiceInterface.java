package com.BankingApp.BankingApplication.Service;

import com.BankingApp.BankingApplication.DTO.*;

public interface UserServiceInterface {

    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest request);
    String nameEnquiry(EnquiryRequest request);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
    BankResponse transfer(TransferRequest request);
    BankResponse login(LoginDTO loginDTO);
}
