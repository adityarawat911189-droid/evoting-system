package com.evoting.evotingsystem.dto;

import jakarta.validation.constraints.NotBlank;

public class SendOtpRequestDto {

    @NotBlank(message = "EPIC number is required")
    private String epicNumber;

    @NotBlank(message = "Email or mobile is required")
    private String emailOrMobile;

    public String getEpicNumber() {
        return epicNumber;
    }

    public void setEpicNumber(String epicNumber) {
        this.epicNumber = epicNumber;
    }

    public String getEmailOrMobile() {
        return emailOrMobile;
    }

    public void setEmailOrMobile(String emailOrMobile) {
        this.emailOrMobile = emailOrMobile;
    }
}