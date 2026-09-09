package com.evoting.evotingsystem.pojo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "national_voter_registry")
public class Voter {

    @Id
    @Column(name = "epic_number", length = 20)
    private String epicNumber;

    @Column(name = "aadhaar_hash", nullable = false, unique = true, length = 256)
    private String aadhaarHash;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "constituency_id", nullable = false, length = 20)
    private String constituencyId;

    @Column(name = "mobile_number_hash", nullable = false, length = 256)
    private String mobileNumberHash;

    @Column(name = "password_hash", length = 256)
    private String passwordHash;

    @Column(name = "pin_hash", length = 256)
    private String pinHash;

    @Column(name = "face_registered")
    private Boolean faceRegistered = false;

    @Column(name = "is_eligible")
    private Boolean isEligible = true;

    @Column(name = "has_voted")
    private Boolean hasVoted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // ---------- Constructors ----------

    public Voter() {
    }

    // ---------- Getters and Setters ----------

    public String getEpicNumber() {
        return epicNumber;
    }

    public void setEpicNumber(String epicNumber) {
        this.epicNumber = epicNumber;
    }

    public String getAadhaarHash() {
        return aadhaarHash;
    }

    public void setAadhaarHash(String aadhaarHash) {
        this.aadhaarHash = aadhaarHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getConstituencyId() {
        return constituencyId;
    }

    public void setConstituencyId(String constituencyId) {
        this.constituencyId = constituencyId;
    }

    public String getMobileNumberHash() {
        return mobileNumberHash;
    }

    public void setMobileNumberHash(String mobileNumberHash) {
        this.mobileNumberHash = mobileNumberHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPinHash() {
        return pinHash;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }

    public Boolean getFaceRegistered() {
        return faceRegistered;
    }

    public void setFaceRegistered(Boolean faceRegistered) {
        this.faceRegistered = faceRegistered;
    }

    public Boolean getIsEligible() {
        return isEligible;
    }

    public void setIsEligible(Boolean isEligible) {
        this.isEligible = isEligible;
    }

    public Boolean getHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(Boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}