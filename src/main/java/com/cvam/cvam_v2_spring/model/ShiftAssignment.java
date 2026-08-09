package com.cvam.cvam_v2_spring.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shift_assignments")

public class ShiftAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)//Best practice for performance
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffProfile staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorProfile doctor;

    @Column(nullable = false)
    private LocalDateTime shiftStart;

    @Column(nullable = false)
    private LocalDateTime shiftEnd;

    protected ShiftAssignment() {
        //for JPA
    }

    public ShiftAssignment(StaffProfile staff, DoctorProfile doctor, LocalDateTime shiftStart, LocalDateTime shiftEnd) {
        //VALIDATION
        if (staff == null) {
            throw new IllegalArgumentException("Staff profile is required.");
        }
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor profile is required.");
        }
        if (shiftStart == null || shiftEnd == null) {
            throw new IllegalArgumentException("Shift start and end times are required.");
        }
        if (shiftEnd.isBefore(shiftStart)) {
            throw new IllegalArgumentException("Shift end time cannot be before shift start time.");
        }

        this.staff = staff;
        this.doctor = doctor;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public StaffProfile getStaff() {
        return staff;
    }

    public DoctorProfile getDoctor() {
        return doctor;
    }

    public LocalDateTime getShiftStart() {
        return shiftStart;
    }

    public LocalDateTime getShiftEnd() {
        return shiftEnd;
    }


}
