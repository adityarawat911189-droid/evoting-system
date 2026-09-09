package com.evoting.evotingsystem.dao;

import com.evoting.evotingsystem.pojo.Voter;
import java.util.Optional;

public interface VoterDao {

    Optional<Voter> findByEpicNumber(String epicNumber);

    Optional<Voter> findByEpicNumberAndAadhaarHash(String epicNumber, String aadhaarHash);

    Voter save(Voter voter);

    boolean existsByEpicNumber(String epicNumber);
}