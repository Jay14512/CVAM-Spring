package com.cvam.cvam_v2_spring.model;


import jakarta.persistence.*;

import javax.print.Doc;

@Entity
@Table(name="doctors")
public class Doctor extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String doctorId;

    protected Doctor(){
        //Required by JPA
    }

    public Doctor(String firstName, String lastName, String fiscalCode, String email, String doctorId) {
        super(firstName, lastName, fiscalCode, email);

        //VALIDATION
        if (doctorId == null || doctorId.isEmpty()) {
            throw new IllegalArgumentException("Doctor ID is required.");
        }

        this.doctorId = doctorId;
    }

    public String getDoctorId() {
        return doctorId;
    }

}
