package com.cvam.cvam_v2_spring.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@SuppressWarnings({"ClassCanBeRecord"})

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

    @ManyToOne
    @JoinColumn(name = "citizen_id", nullable = false)
    private Citizen citizen;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    /**
     * The scheduled date and time for the vaccination.
     * Assumption: Uses LocalDateTime, implying all operations occur within the local time zone of the clinic.
     * Time zone offsets are omitted.
     */

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String vaccineType;

    protected Appointment() {
        //for JPA
    }

    public Appointment(String appointmentId, Citizen citizen, Doctor doctor, LocalDateTime dateTime, String vaccineType) {
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
        this.citizen = citizen;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.vaccineType = vaccineType;

    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getVaccineType() {
        return vaccineType;
    }
}
