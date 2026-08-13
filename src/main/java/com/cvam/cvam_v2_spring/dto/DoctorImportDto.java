package com.cvam.cvam_v2_spring.dto;

public class DoctorImportDto {
    private String userFiscalCode;
    private String medicalLicenseNumber;
    private String officePhoneNumber;

    public DoctorImportDto() {

    }

    //User Fiscal Code
    public String getUserFiscalCode() {
        return userFiscalCode;
    }

    public void setUserFiscalCode(String userFiscalCode) {
        this.userFiscalCode = userFiscalCode;
    }

    //Medical License Number
    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    public void setMedicalLicenseNumber(String medicalLicenseNumber) {
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    //Office Phone Number
    public String getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public void setOfficePhoneNumber(String officePhoneNumber) {
        this.officePhoneNumber = officePhoneNumber;
    }
}
