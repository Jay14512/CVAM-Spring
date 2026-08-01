package com.cvam.cvam_v2_spring.model;


public abstract class User {
    private final String firstName;
    private final String lastName;
    private final String fiscalCode;
    private final String email;

    public User(String firstName, String lastName, String fiscalCode, String email) {

        //VALIDATION
        //First Name
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty.");
        }

        //Last Name
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty.");
        }

        //Fiscal Code
        if (fiscalCode == null || fiscalCode.isEmpty()) {
            throw new IllegalArgumentException("Fiscal Code cannot be empty.");
        }

        //Email
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email is invalid.");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.fiscalCode = fiscalCode;
        this.email = email;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public String getEmail() {
        return email;
    }


}



