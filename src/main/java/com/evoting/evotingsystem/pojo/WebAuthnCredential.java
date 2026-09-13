package com.evoting.evotingsystem.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "webauthn_credentials")
public class WebAuthnCredential {

    @Id
    @Column(name = "credential_id", length = 512)
    private String credentialId;

    @Column(name = "epic_number", nullable = false, length = 20)
    private String epicNumber;

    @Column(name = "public_key_cose", columnDefinition = "TEXT", nullable = false)
    private String publicKeyCose;

    @Column(name = "sign_count")
    private Long signCount = 0L;

    public WebAuthnCredential() {
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getEpicNumber() {
        return epicNumber;
    }

    public void setEpicNumber(String epicNumber) {
        this.epicNumber = epicNumber;
    }

    public String getPublicKeyCose() {
        return publicKeyCose;
    }

    public void setPublicKeyCose(String publicKeyCose) {
        this.publicKeyCose = publicKeyCose;
    }

    public Long getSignCount() {
        return signCount;
    }

    public void setSignCount(Long signCount) {
        this.signCount = signCount;
    }
}