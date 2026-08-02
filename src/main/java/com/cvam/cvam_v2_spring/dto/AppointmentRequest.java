package com.cvam.cvam_v2_spring.dto;

import java.time.LocalDateTime;

public class AppointmentRequest {
    private String appointmentId;
    private String fiscalCode;
    private String doctorId;
    private LocalDateTime dateTime;
    private String vaccineType;

    //Standard default constructor for Jackson
    public AppointmentRequest(){}

    //Getters and Setters, so Jackson can populate the fields
    //Appointment ID
    public String getAppointmentId(){return appointmentId;}
    public void setAppointmentId(String appointmentId){this.appointmentId = appointmentId;}

    //Fiscal Code
    public String getFiscalCode(){return fiscalCode;}
    public void setFiscalCode(String fiscalCode){this.fiscalCode = fiscalCode;}

    //Doctor ID
    public String getDoctorId(){return doctorId;}
    public void setDoctorId(String doctorId){this.doctorId = doctorId;}

    //Date-Time
    public LocalDateTime getDateTime(){return dateTime;}
    public void setDateTime(LocalDateTime dateTime){this.dateTime = dateTime;}

    //Vaccine Type

    public String getVaccineType() {return vaccineType;}
    public void setVaccineType(String vaccineType) {this.vaccineType = vaccineType;}
}
