package com.BankingApp.BankingApplication.Utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "400";

    public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account";

    public static final String ACCOUNT_CREATED_STATUS_CODE = "201";

    public static final String ACCOUNT_CREATED_MESSAGE = "Account created successfully";

    public static final String ACCOUNT_NOT_EXIST_CODE = "003";

    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "User with the provided Account Number does not exist";

    public static final String ACCOUNT_FOUND_CODE = "004";

    public static final String ACCOUNT_FOUND_SUCCESS = "User Account Found";

    public static final String ACCOUNT_CREDITED_SUCCESS = "005";

    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "User Account was credited successfully";

    public static final String INSUFFICIENT_BALANCE_CODE = "006";

    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance";

    public static final String ACCOUNT_DEBITED_SUCCESS = "007";

    public static final String ACCOUNT_DEBITED_MESSAGE = "Account has been successfully debited";

    public static final String SOURCE_ACCOUNT_NOT_EXIST_STATUS_CODE = "008";

    public static final String SOURCE_ACCOUNT_NOT_EXIST_MESSAGE = "The source account doesn't exist";

    public static final String DESTINATION_ACCOUNT_NOT_EXIST_STATUS_CODE = "009";

    public static final String DESTINATION_ACCOUNT_NOT_EXIST_MESSAGE = "The destination account doesn't exist";

    public static final String TRANSFER_SUCCESSFUL_STATUS_CODE = "010";

    public static final String TRANSFER_SUCCESSFUL_MESSAGE = "The fund transfer was successful";

    public static final String LOGIN_SUCCESS_STATUS_CODE = "011";

    public static final String LOGIN_SUCCESS_MESSAGE = "You have logged in";

    public static String generateAccountNumber(){
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        int randNumber =(int) Math.floor(Math.random() * (max - min + 1) + min);

        String year = String.valueOf(currentYear);
        String number = String.valueOf(randNumber);

        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(year).append(number).toString();

    }
}
