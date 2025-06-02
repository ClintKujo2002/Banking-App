package com.BankingApp.BankingApplication.DTO;

import lombok.*;


public class UserRequest {
    private String firstName;

    private String lastName;

    private String middleName;

    private String gender;

    private String address;

    private String stateOfOrigin;

    private String email;

    private String password;

    private String phoneNumber;

    private String alternativePhoneNumber;


    public UserRequest(){}

    public UserRequest(String firstName, String lastName, String middleName, String gender, String address, String stateOfOrigin, String email, String password, String phoneNumber, String alternativePhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.address = address;
        this.stateOfOrigin = stateOfOrigin;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.alternativePhoneNumber = alternativePhoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStateOfOrigin() {
        return stateOfOrigin;
    }

    public void setStateOfOrigin(String stateOfOrigin) {
        this.stateOfOrigin = stateOfOrigin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlternativePhoneNumber() {
        return alternativePhoneNumber;
    }

    public void setAlternativePhoneNumber(String alternativePhoneNumber) {
        this.alternativePhoneNumber = alternativePhoneNumber;
    }
}
