package com.cvam.cvam_v2_spring.model;


import jakarta.persistence.*;

@Entity
@Table(name = "staff_profiles")
public class StaffProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String staffCode;

    //Links the job instance back to the physical person

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //Links this job to a specific doctor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorProfile doctor;

    protected StaffProfile() {
        //Required by JPA
    }

    public StaffProfile(String staffCode, User user, DoctorProfile doctor) {

        //VALIDATION
        //Staff Code
        if (staffCode == null || staffCode.isEmpty()) {
            throw new IllegalArgumentException("Staff code is required.");
        }

        //User
        if (user == null) {
            throw new IllegalArgumentException("User association required ");
        }
        //Doctor ID
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor association required.");
        }

        this.staffCode = staffCode;
        this.user = user;
        this.doctor = doctor;

    }

    public Long getId() {
        return id;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public User getUser() {
        return user;
    }

    public DoctorProfile getDoctor() {
        return doctor;
    }

}
