package com.evoting.evotingsystem.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterVerifyRequestDto {

    @NotBlank(message = "EPIC number is required")
    private String epicNumber;

    @NotBlank(message = "Aadhaar number is required")
    private String aadhaarNumber;

    @NotBlank(message = "Date of birth is required")
    private String dateOfBirth;

    // ---------- Getters and Setters ----------

    public String getEpicNumber() {
        return epicNumber;
    }

    public void setEpicNumber(String epicNumber) {
        this.epicNumber = epicNumber;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}