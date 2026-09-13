package com.evoting.evotingsystem.daoimpl;

import com.evoting.evotingsystem.dao.WebAuthnCredentialDao;
import com.evoting.evotingsystem.pojo.WebAuthnCredential;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class WebAuthnCredentialDaoImpl implements WebAuthnCredentialDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public WebAuthnCredential save(WebAuthnCredential credential) {
        entityManager.persist(credential);
        return credential;
    }

    @Override
    public List<WebAuthnCredential> findByEpicNumber(String epicNumber) {
        TypedQuery<WebAuthnCredential> query = entityManager.createQuery(
                "SELECT c FROM WebAuthnCredential c WHERE c.epicNumber = :epicNumber",
                WebAuthnCredential.class
        );
        query.setParameter("epicNumber", epicNumber);
        return query.getResultList();
    }

    @Override
    public Optional<WebAuthnCredential> findByCredentialId(String credentialId) {
        WebAuthnCredential credential = entityManager.find(WebAuthnCredential.class, credentialId);
        return Optional.ofNullable(credential);
    }
}