package com.evoting.evotingsystem.daoimpl;

import com.evoting.evotingsystem.dao.WebAuthnChallengeDao;
import com.evoting.evotingsystem.pojo.WebAuthnChallenge;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class WebAuthnChallengeDaoImpl implements WebAuthnChallengeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public WebAuthnChallenge save(WebAuthnChallenge challenge) {
        WebAuthnChallenge existing = entityManager.find(WebAuthnChallenge.class, challenge.getEpicNumber());
        if (existing != null) {
            entityManager.remove(existing);
            entityManager.flush();
        }
        entityManager.persist(challenge);
        return challenge;
    }

    @Override
    public Optional<WebAuthnChallenge> findByEpicNumber(String epicNumber) {
        WebAuthnChallenge challenge = entityManager.find(WebAuthnChallenge.class, epicNumber);
        return Optional.ofNullable(challenge);
    }

    @Override
    @Transactional
    public void deleteByEpicNumber(String epicNumber) {
        WebAuthnChallenge challenge = entityManager.find(WebAuthnChallenge.class, epicNumber);
        if (challenge != null) {
            entityManager.remove(challenge);
        }
    }
}