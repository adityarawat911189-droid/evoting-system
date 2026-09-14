package com.evoting.evotingsystem.dto;

import jakarta.validation.constraints.NotBlank;

public class VoteRequestDto {

    @NotBlank(message = "Voting token is required")
    private String token;

    @NotBlank(message = "Candidate ID is required")
    private String candidateId;

    @NotBlank(message = "PIN is required")
    private String pin;

    @NotBlank(message = "Fingerprint credential ID is required")
    private String credentialId;

    @NotBlank(message = "Authenticator data is required")
    private String authenticatorData;

    @NotBlank(message = "Client data is required")
    private String clientDataJSON;

    @NotBlank(message = "Signature is required")
    private String signature;

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

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getAuthenticatorData() {
        return authenticatorData;
    }

    public void setAuthenticatorData(String authenticatorData) {
        this.authenticatorData = authenticatorData;
    }

    public String getClientDataJSON() {
        return clientDataJSON;
    }

    public void setClientDataJSON(String clientDataJSON) {
        this.clientDataJSON = clientDataJSON;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}