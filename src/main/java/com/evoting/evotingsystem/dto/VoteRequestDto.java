package com.evoting.evotingsystem.dto;

import jakarta.validation.constraints.NotBlank;

public class VoteRequestDto {

    @NotBlank(message = "Voting token is required")
    private String token;

    @NotBlank(message = "Candidate ID is required")
    private String candidateId;

    @NotBlank(message = "PIN is required")
    private String pin;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}