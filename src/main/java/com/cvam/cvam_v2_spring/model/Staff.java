package com.cvam.cvam_v2_spring.model;


import jakarta.persistence.*;

@Entity
@Table(name = "staff")
public class Staff extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String staffCode;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    protected Staff() {
        //Required by JPA
    }

    public Staff(String firstName, String lastName, String fiscalCode, String email, String staffCode, Doctor doctor) {
        super(firstName, lastName, fiscalCode, email);

        //VALIDATION
        //Staff Code
        if (staffCode == null || staffCode.isEmpty()) {
            throw new IllegalArgumentException("Staff code is required.");
        }

        //Doctor ID
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor ID is required.");
        }

        this.staffCode = staffCode;
        this.doctor = doctor;

    }

    public Long getId() {
        return id;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public Doctor getDoctor() {
        return doctor;
    }

}
