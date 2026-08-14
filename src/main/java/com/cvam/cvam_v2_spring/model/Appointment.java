package com.cvam.cvam_v2_spring.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;



@Entity
@Table(name = "appointments")
public class Appointment {

    /**
     * Unique identifier for the appointment.
     * Assumption: Handled as case-insensitive alphanumeric strings (e.g., "APPT001").
     * Uniqueness is strictly enforced at the service registry layer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String appointmentId;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String vaccineType;


    //LINK TO CITIZEN
    @ManyToOne(fetch = FetchType.LAZY)//Lazy loading is best practice for performance
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //LINK TO DOCTOR
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorProfile doctor;

    /**
     * The scheduled date and time for the vaccination.
     * Assumption: Uses LocalDateTime, implying all operations occur within the local time zone of the clinic.
     * Time zone offsets are omitted.
     */


    protected Appointment() {
        //for JPA
    }

    public Appointment(String appointmentId, User citizen, DoctorProfile doctor, LocalDateTime dateTime, String vaccineType) {
        //VALIDATION
        //Appointment ID
        if (appointmentId == null || appointmentId.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID cannot be empty.");
        }

        //Citizen
        if (citizen == null) {
            throw new IllegalArgumentException("Citizen cannot be empty.");
        }

        //Doctor
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be empty.");
        }

        //DateTime
        if (dateTime == null) {
            throw new IllegalArgumentException("Date cannot be empty.");
        }

        //Vaccine Type
        if (vaccineType == null || vaccineType.isEmpty()) {
            throw new IllegalArgumentException("Vaccine Type must be defined.");
        }


        this.appointmentId = appointmentId;
        this.user = user;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.vaccineType = vaccineType;

    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public User getUser() {
        return user;
    }

    public DoctorProfile getDoctor() {
        return doctor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getVaccineType() {
        return vaccineType;
    }
}
