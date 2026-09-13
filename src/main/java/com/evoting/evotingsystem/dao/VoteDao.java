package com.evoting.evotingsystem.dao;

import com.evoting.evotingsystem.pojo.Vote;
import java.util.List;
import java.util.Optional;

public interface VoteDao {

    Vote save(Vote vote);

    Optional<Vote> findLatestVote();

    long countByCandidateId(String candidateId);

    List<Vote> findAllOrderedById();
}