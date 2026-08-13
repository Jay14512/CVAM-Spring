package com.cvam.cvam_v2_spring.dto;

public class StaffImportDto {
    private String userFiscalCode;
    private String staffCode;

    public StaffImportDto() {

    }

    //User Fiscal Code
    public String getUserFiscalCode() {
        return userFiscalCode;
    }

    public void setUserFiscalCode(String userFiscalCode) {
        this.userFiscalCode = userFiscalCode;
    }

    //Staff Code
    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }
}
