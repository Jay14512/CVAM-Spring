package com.cvam.cvam_v2_spring.dto;

import java.time.LocalDateTime;

public class AppointmentImportDto {
    private String appointmentId;
    private LocalDateTime dateTime;
    private String vaccineType;
    private String citizenFiscalCode;
    private String doctorLicenseNumber;

    public AppointmentImportDto() {

    }

    //Appointment ID
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    //Date Time
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    //Vaccine Type
    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    //Citizen Fiscal Code
    public String getCitizenFiscalCode() {
        return citizenFiscalCode;
    }

    public void setCitizenFiscalCode(String citizenFiscalCode) {
        this.citizenFiscalCode = citizenFiscalCode;
    }

    //Doctor License Number
    public String getDoctorLicenseNumber() {
        return doctorLicenseNumber;
    }

    public void setDoctorLicenseNumber(String doctorLicenseNumber) {
        this.doctorLicenseNumber = doctorLicenseNumber;
    }
}
