package com.evoting.evotingsystem.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @Column(name = "candidate_id", length = 20)
    private String candidateId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "party", length = 100)
    private String party;

    @Column(name = "constituency_id", nullable = false, length = 20)
    private String constituencyId;

    @Column(name = "manifesto", columnDefinition = "TEXT")
    private String manifesto;

    // ---------- Constructors ----------

    public Candidate() {
    }

    // ---------- Getters and Setters ----------

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getConstituencyId() {
        return constituencyId;
    }

    public void setConstituencyId(String constituencyId) {
        this.constituencyId = constituencyId;
    }

    public String getManifesto() {
        return manifesto;
    }

    public void setManifesto(String manifesto) {
        this.manifesto = manifesto;
    }
}