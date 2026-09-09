package com.evoting.evotingsystem.dto;

import jakarta.validation.constraints.NotBlank;

public class VerifyOtpRequestDto {

    @NotBlank(message = "EPIC number is required")
    private String epicNumber;

    @NotBlank(message = "OTP is required")
    private String otp;

    public String getEpicNumber() {
        return epicNumber;
    }

    public void setEpicNumber(String epicNumber) {
        this.epicNumber = epicNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}