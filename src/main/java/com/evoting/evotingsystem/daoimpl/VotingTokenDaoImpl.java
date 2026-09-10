package com.evoting.evotingsystem.daoimpl;

import com.evoting.evotingsystem.dao.VotingTokenDao;
import com.evoting.evotingsystem.pojo.VotingToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class VotingTokenDaoImpl implements VotingTokenDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public VotingToken save(VotingToken token) {
        entityManager.persist(token);
        return token;
    }

    @Override
    public Optional<VotingToken> findByTokenHash(String tokenHash) {
        VotingToken token = entityManager.find(VotingToken.class, tokenHash);
        return Optional.ofNullable(token);
    }

    @Override
    @Transactional
    public boolean markTokenAsUsed(String tokenHash) {
        Query query = entityManager.createQuery(
                "UPDATE VotingToken t SET t.isUsed = true " +
                        "WHERE t.tokenHash = :tokenHash AND t.isUsed = false"
        );
        query.setParameter("tokenHash", tokenHash);

        int rowsUpdated = query.executeUpdate();

        return rowsUpdated > 0;
    }

    @Override
    @Transactional
    public void deleteToken(String tokenHash) {
        VotingToken token = entityManager.find(VotingToken.class, tokenHash);
        if (token != null) {
            entityManager.remove(token);
        }
    }
}