package com.cvam.cvam_v2_spring.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "citizen_profiles")
public class CitizenProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate birthDate;

    //Connects this citizen profile to the physical person
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;


    @OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    protected CitizenProfile() {
        //for JPA
    }

    public CitizenProfile(User user, String phoneNumber, LocalDate birthDate) {

        //VALIDATION
        //User
        if (user == null) {
            throw new IllegalArgumentException("User is required.");
        }

        //Phone Number
        if (phoneNumber == null || !phoneNumber.startsWith("+")) {
            throw new IllegalArgumentException("Phone number is invalid.");
        }

        //Birthdate
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date is required.");
        }

        this.user = user;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }


}
