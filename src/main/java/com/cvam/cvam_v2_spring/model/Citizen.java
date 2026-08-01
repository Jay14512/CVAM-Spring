package com.cvam.cvam_v2_spring.model;

import java.time.LocalDate;


public class Citizen extends User {
    private final String phoneNumber;
    private final LocalDate birthDate;

    public Citizen(String firstName, String lastName, String fiscalCode, String email, String phoneNumber, LocalDate birthDate) {
        super(firstName, lastName, fiscalCode, email);


        //VALIDATION
        //Phone Number
        if (phoneNumber == null || !phoneNumber.startsWith("+")) {
            throw new IllegalArgumentException("Phone number is invalid.");
        }

        //Birthdate
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date is required.");
        }

        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }


}
