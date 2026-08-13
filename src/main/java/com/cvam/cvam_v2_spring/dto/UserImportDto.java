package com.cvam.cvam_v2_spring.dto;

import java.time.LocalDate;

public class UserImportDto {
    private String firstName;
    private String lastName;
    private String fiscalCode;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;


    public UserImportDto(){

    }

    //First Name
    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    //Last Name
    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //Fiscal Code
    public String getFiscalCode(){
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    //Email
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    //Phone Number

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //Birth Date

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
