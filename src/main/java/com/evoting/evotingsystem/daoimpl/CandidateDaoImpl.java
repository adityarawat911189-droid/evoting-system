package com.evoting.evotingsystem.daoimpl;

import com.evoting.evotingsystem.dao.CandidateDao;
import com.evoting.evotingsystem.pojo.Candidate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CandidateDaoImpl implements CandidateDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Candidate> findByConstituencyId(String constituencyId) {
        TypedQuery<Candidate> query = entityManager.createQuery(
                "SELECT c FROM Candidate c WHERE c.constituencyId = :constituencyId",
                Candidate.class
        );
        query.setParameter("constituencyId", constituencyId);
        return query.getResultList();
    }

    @Override
    public Optional<Candidate> findById(String candidateId) {
        Candidate candidate = entityManager.find(Candidate.class, candidateId);
        return Optional.ofNullable(candidate);
    }

    @Override
    @Transactional
    public Candidate save(Candidate candidate) {
        entityManager.persist(candidate);
        return candidate;
    }
}