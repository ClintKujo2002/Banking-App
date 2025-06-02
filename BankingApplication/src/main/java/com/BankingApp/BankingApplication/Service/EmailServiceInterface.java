package com.BankingApp.BankingApplication.Service;

import com.BankingApp.BankingApplication.DTO.EmailDetails;

public interface EmailServiceInterface {

    public void sendEmailAlert(EmailDetails emailDetails);

    void sendEmailWithAttachment(EmailDetails emailDetails);
}
