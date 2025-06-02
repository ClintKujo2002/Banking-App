package com.BankingApp.BankingApplication.Controller;

import com.BankingApp.BankingApplication.DTO.*;
import com.BankingApp.BankingApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/accountCreation")
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
            return userService.createAccount(userRequest);
    }

    @PostMapping("/login")
    public BankResponse login(@RequestBody LoginDTO loginDTO){
            return userService.login(loginDTO);
    }

    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestParam String accountNumber) {
        EnquiryRequest request = new EnquiryRequest();
        request.setAccountNumber(accountNumber);
        return userService.balanceEnquiry(request);
    }

    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestParam String accountNumber) {
        EnquiryRequest request = new EnquiryRequest();
        request.setAccountNumber(accountNumber);
        return userService.nameEnquiry(request);
    }

    @PostMapping("/credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }

    @PostMapping("/debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }

    @PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest request){
        return userService.transfer(request);
    }
}
