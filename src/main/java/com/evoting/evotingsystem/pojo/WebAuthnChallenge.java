package com.evoting.evotingsystem.pojo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "webauthn_challenges")
public class WebAuthnChallenge {

    @Id
    @Column(name = "epic_number", length = 20)
    private String epicNumber;

    @Column(name = "challenge", nullable = false, length = 512)
    private String challenge;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public WebAuthnChallenge() {
    }

    public String getEpicNumber() {
        return epicNumber;
    }

    public void setEpicNumber(String epicNumber) {
        this.epicNumber = epicNumber;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}