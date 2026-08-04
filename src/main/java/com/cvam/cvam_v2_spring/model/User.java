package com.cvam.cvam_v2_spring.model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String fiscalCode;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate birthDate;

    protected User() {
        //for JPA
    }

    public User(String firstName, String lastName, String fiscalCode, String email, String phoneNumber, LocalDate birthDate) {

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

        //Phone Number
        if (phoneNumber == null || !phoneNumber.startsWith("+")) {
            throw new IllegalArgumentException("Phone number is invalid.");
        }

        //Birthdate
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date is required.");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.fiscalCode = fiscalCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;

    }

    public Long getId() {
        return id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}



