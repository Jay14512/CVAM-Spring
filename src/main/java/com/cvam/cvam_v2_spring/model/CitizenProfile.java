package com.cvam.cvam_v2_spring.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "citizen_profiles")
public class CitizenProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //Connects this citizen profile to the physical person
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;


    @OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    protected CitizenProfile() {
        //for JPA
    }

    public CitizenProfile(User user) {

        //VALIDATION
        //User
        if (user == null) {
            throw new IllegalArgumentException("User is required.");
        }

        this.user = user;

    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }


}
