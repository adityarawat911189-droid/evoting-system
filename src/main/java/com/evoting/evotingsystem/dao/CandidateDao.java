package com.evoting.evotingsystem.dao;

import com.evoting.evotingsystem.pojo.Candidate;
import java.util.List;
import java.util.Optional;

public interface CandidateDao {

    List<Candidate> findByConstituencyId(String constituencyId);

    Optional<Candidate> findById(String candidateId);

    Candidate save(Candidate candidate);
}