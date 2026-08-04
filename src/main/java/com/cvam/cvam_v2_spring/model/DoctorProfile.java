package com.cvam.cvam_v2_spring.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor_profiles")
public class DoctorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String medicalLicenseNumber;

    @Column(name = "office_phone_number", nullable = true)
    private String officePhoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    protected DoctorProfile() {
        //Required by JPA
    }

    public DoctorProfile(User user, String medicalLicenseNumber, String officePhoneNumber) {
        //VALIDATION
        //User
        if (user == null) {
            throw new IllegalArgumentException("User is required.");
        }

        if (medicalLicenseNumber == null || medicalLicenseNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("License Number is required");
        }

        this.user = user;
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.officePhoneNumber = officePhoneNumber;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    public String getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
