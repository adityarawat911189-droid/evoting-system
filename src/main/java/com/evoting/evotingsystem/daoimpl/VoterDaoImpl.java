package com.evoting.evotingsystem.daoimpl;

import com.evoting.evotingsystem.dao.VoterDao;
import com.evoting.evotingsystem.pojo.Voter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class VoterDaoImpl implements VoterDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Voter> findByEpicNumber(String epicNumber) {
        Voter voter = entityManager.find(Voter.class, epicNumber);
        return Optional.ofNullable(voter);
    }

    @Override
    public Optional<Voter> findByEpicNumberAndAadhaarHash(String epicNumber, String aadhaarHash) {
        try {
            TypedQuery<Voter> query = entityManager.createQuery(
                    "SELECT v FROM Voter v WHERE v.epicNumber = :epicNumber AND v.aadhaarHash = :aadhaarHash",
                    Voter.class
            );
            query.setParameter("epicNumber", epicNumber);
            query.setParameter("aadhaarHash", aadhaarHash);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Voter save(Voter voter) {
        if (entityManager.find(Voter.class, voter.getEpicNumber()) == null) {
            entityManager.persist(voter);
        } else {
            voter = entityManager.merge(voter);
        }
        return voter;
    }

    @Override
    public boolean existsByEpicNumber(String epicNumber) {
        return entityManager.find(Voter.class, epicNumber) != null;
    }
}