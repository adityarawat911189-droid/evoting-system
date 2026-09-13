package com.evoting.evotingsystem.dao;

import com.evoting.evotingsystem.pojo.WebAuthnChallenge;
import java.util.Optional;

public interface WebAuthnChallengeDao {

    WebAuthnChallenge save(WebAuthnChallenge challenge);

    Optional<WebAuthnChallenge> findByEpicNumber(String epicNumber);

    void deleteByEpicNumber(String epicNumber);
}