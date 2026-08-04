package com.cvam.cvam_v2_spring.model;


import jakarta.persistence.*;

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

    protected User() {
        //for JPA
    }

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


}



