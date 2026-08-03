package com.cvam.cvam_v2_spring.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "citizens")
public class Citizen extends User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  String phoneNumber;

    @Column(nullable = false)
    private  LocalDate birthDate;


    protected Citizen(){
        //for JPA
    }

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
