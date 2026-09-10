package com.evoting.evotingsystem.dao;

import com.evoting.evotingsystem.pojo.VotingToken;
import java.util.Optional;

public interface VotingTokenDao {

    VotingToken save(VotingToken token);

    Optional<VotingToken> findByTokenHash(String tokenHash);

    boolean markTokenAsUsed(String tokenHash);

    void deleteToken(String tokenHash);
}