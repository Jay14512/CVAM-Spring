package com.cvam.cvam_v2_spring.model;



public class Staff extends User {
    private final String staffCode;
    private final String doctorId;

    public Staff(String firstName, String lastName, String fiscalCode, String email, String staffCode, String doctorId) {
        super(firstName, lastName, fiscalCode, email);

        //VALIDATION
        //Staff Code
        if (staffCode == null || staffCode.isEmpty()) {
            throw new IllegalArgumentException("Staff code is required.");
        }

        //Doctor ID
        if (doctorId == null || doctorId.isEmpty()) {
            throw new IllegalArgumentException("Doctor ID is required.");
        }

        this.staffCode = staffCode;
        this.doctorId = doctorId;

    }

    public String getStaffCode() {
        return staffCode;
    }

    public String getDoctorId() {
        return doctorId;
    }

}
