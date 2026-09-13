package com.evoting.evotingsystem.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "elections")
public class Election {

    @Id
    @Column(name = "election_id", length = 20)
    private String electionId;

    @Column(name = "constituency_id", nullable = false, length = 20)
    private String constituencyId;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    // ---------- Constructors ----------

    public Election() {
    }

    // ---------- Getters and Setters ----------

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public String getConstituencyId() {
        return constituencyId;
    }

    public void setConstituencyId(String constituencyId) {
        this.constituencyId = constituencyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}