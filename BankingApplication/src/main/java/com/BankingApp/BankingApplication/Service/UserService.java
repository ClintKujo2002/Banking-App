package com.BankingApp.BankingApplication.Service;

import com.BankingApp.BankingApplication.Config.JwtTokenProvider;
import com.BankingApp.BankingApplication.DTO.*;
import com.BankingApp.BankingApplication.Entity.Role;
import com.BankingApp.BankingApplication.Entity.Transaction;
import com.BankingApp.BankingApplication.Entity.User;
import com.BankingApp.BankingApplication.Repository.UserRepository;
import com.BankingApp.BankingApplication.Utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        // Check if the email already exists in the database
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return new BankResponse(
                    AccountUtils.ACCOUNT_EXISTS_CODE,
                    AccountUtils.ACCOUNT_EXISTS_MESSAGE,
                    null
            );
        }

        // Create a new User object
        User newUser = new User();
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastName(userRequest.getLastName());
        newUser.setMiddleName(userRequest.getMiddleName());
        newUser.setGender(userRequest.getGender());
        newUser.setAddress(userRequest.getAddress());
        newUser.setStateOfOrigin(userRequest.getStateOfOrigin());
        newUser.setAccountNumber(AccountUtils.generateAccountNumber());
        newUser.setAccountBalance(BigDecimal.ZERO);
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setPhoneNumber(userRequest.getPhoneNumber());
        newUser.setAlternativePhoneNumber(userRequest.getAlternativePhoneNumber());
        newUser.setStatus("ACTIVE");
        newUser.setRole(Role.valueOf("ADMIN"));

        // Save the new user to the database
        User savedUser = userRepository.save(newUser);

        // Build and return the BankResponse
        AccountInfo accountInfo = new AccountInfo(
                savedUser.getFirstName() + " " + savedUser.getMiddleName() + " " + savedUser.getLastName(),
                savedUser.getAccountBalance(),
                savedUser.getAccountNumber()
        );

        EmailDetails emailDetails = new EmailDetails();
                emailDetails.setRecipient(savedUser.getEmail());
                emailDetails.setSubject("ACCOUNT CREATION");
                emailDetails.setMessageBody("Congratulations! Your account has been created.\nYour account details: \n" +
                        "Account name: " + savedUser.getFirstName() + " " + savedUser.getMiddleName() + " " + savedUser.getLastName() + "\nAccount number: " + savedUser.getAccountNumber());
        emailService.sendEmailAlert(emailDetails);
        return new BankResponse(
                AccountUtils.ACCOUNT_CREATED_STATUS_CODE,
                AccountUtils.ACCOUNT_CREATED_MESSAGE,
                accountInfo
        );
    }

    public BankResponse login(LoginDTO loginDTO){
        Authentication authentication = null;
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        EmailDetails loginAlert = new EmailDetails();
        loginAlert.setSubject("You've logged in");
        loginAlert.setRecipient(loginDTO.getEmail());
        loginAlert.setMessageBody("You have logged into the banking application");

        emailService.sendEmailAlert(loginAlert);


        BankResponse bankResponse = new BankResponse();
        bankResponse.setResponseCode(AccountUtils.LOGIN_SUCCESS_STATUS_CODE);
        bankResponse.setResponseMessage(jwtTokenProvider.generateToken(authentication));

        return bankResponse;
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return new BankResponse(
                    AccountUtils.ACCOUNT_NOT_EXIST_CODE,
                    AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE,
                    null
            );
        }

        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());

        String accountName = Stream.of(foundUser.getFirstName(), foundUser.getLastName(), foundUser.getMiddleName())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));

        // Create AccountInfo manually
        AccountInfo accountInfo = new AccountInfo(
                request.getAccountNumber(),
                foundUser.getAccountBalance(),
                accountName
        );
        return new BankResponse(
                AccountUtils.ACCOUNT_FOUND_CODE,
                AccountUtils.ACCOUNT_FOUND_SUCCESS,
                accountInfo

        );
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist){
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getMiddleName();
    }


    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return new BankResponse(
                    AccountUtils.ACCOUNT_NOT_EXIST_CODE,
                    AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE,
                    null
            );
        }
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());

        User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
        userRepository.save(userToCredit);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccountNumber(userToCredit.getAccountNumber());
        transactionDTO.setTransactionType("CREDIT");
        transactionDTO.setAmount(request.getAmount());

        transactionService.saveTransaction(transactionDTO);

        String accountName = Stream.of(foundUser.getFirstName(), foundUser.getLastName(), foundUser.getMiddleName())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));

        // Create AccountInfo manually
        AccountInfo accountInfo = new AccountInfo(
                request.getAccountNumber(),
                foundUser.getAccountBalance(),
                accountName
        );

        // Create and return BankResponse manually
        return new BankResponse(
                AccountUtils.ACCOUNT_CREDITED_SUCCESS,
                AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE,
                accountInfo
        );
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return new BankResponse(
                    AccountUtils.ACCOUNT_NOT_EXIST_CODE,
                    AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE,
                    null
            );
        }
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());

        User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
        BigInteger availableBalance =userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();


        if(availableBalance.intValue() < debitAmount.intValue()){
            return new BankResponse(
                    AccountUtils.INSUFFICIENT_BALANCE_CODE,
                    AccountUtils.INSUFFICIENT_BALANCE_MESSAGE,
                    null
            );
        }

        else{
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
            userRepository.save(userToDebit);

            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAccountNumber(userToDebit.getAccountNumber());
            transactionDTO.setTransactionType("CREDIT");
            transactionDTO.setAmount(request.getAmount());

            transactionService.saveTransaction(transactionDTO);

            String accountName = Stream.of(foundUser.getFirstName(), foundUser.getLastName(), foundUser.getMiddleName())
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" "));

            // Create AccountInfo manually
            AccountInfo accountInfo = new AccountInfo(
                    request.getAccountNumber(),
                    foundUser.getAccountBalance(),
                    accountName
            );

            return new BankResponse(
                    AccountUtils.ACCOUNT_DEBITED_SUCCESS,
                    AccountUtils.ACCOUNT_DEBITED_MESSAGE,
                    accountInfo
                    );

        }
    }

    @Override
    public BankResponse transfer(TransferRequest request) {

        boolean isSourceAccountExist = userRepository.existsByAccountNumber(request.getSourceAccountNumber());
        boolean isDestinationAccountExist = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());

        User sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
        User destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());

        String sourceAccountUsername = sourceAccountUser.getFirstName() + sourceAccountUser.getLastName() + sourceAccountUser.getMiddleName();
        String recipientAccountUsername = destinationAccountUser.getFirstName() + destinationAccountUser.getLastName() + destinationAccountUser.getMiddleName();

        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
        userRepository.save(destinationAccountUser);

        if(!isDestinationAccountExist){
            return new BankResponse(
                    AccountUtils.DESTINATION_ACCOUNT_NOT_EXIST_STATUS_CODE,
                    AccountUtils.DESTINATION_ACCOUNT_NOT_EXIST_MESSAGE,
                    null
            );
        }
        if(!isSourceAccountExist){
            return new BankResponse(
                    AccountUtils.SOURCE_ACCOUNT_NOT_EXIST_STATUS_CODE,
                    AccountUtils.SOURCE_ACCOUNT_NOT_EXIST_MESSAGE,
                    null
            );
        }


        if(request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) < 0){
            return new BankResponse(
                    AccountUtils.INSUFFICIENT_BALANCE_CODE,
                    AccountUtils.INSUFFICIENT_BALANCE_MESSAGE,
                    null
            );
        }

        EmailDetails debitAlert = new EmailDetails();
        debitAlert.setRecipient(sourceAccountUser.getEmail());
        debitAlert.setSubject("DEBIT ALERT");
        debitAlert.setMessageBody("The sum of " + request.getAmount() + " has been debited from your account to " + recipientAccountUsername+"." + "Your current balance is " + sourceAccountUser.getAccountBalance());
        emailService.sendEmailAlert(debitAlert);

        EmailDetails creditAlert = new EmailDetails();
        creditAlert.setRecipient(sourceAccountUser.getEmail());
        creditAlert.setSubject("CREDIT ALERT");
        creditAlert.setMessageBody("The sum of " + request.getAmount() + " has been credited to your account from " + recipientAccountUsername+"." + "Your current balance is " + sourceAccountUser.getAccountBalance());
        emailService.sendEmailAlert(creditAlert);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccountNumber(destinationAccountUser.getAccountNumber());
        transactionDTO.setTransactionType("CREDIT");
        transactionDTO.setAmount(request.getAmount());

        transactionService.saveTransaction(transactionDTO);

        return new BankResponse(
                AccountUtils.TRANSFER_SUCCESSFUL_STATUS_CODE,
                AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE,
                null

        );
    }

}
