package com.evoting.evotingsystem.daoimpl;

import com.evoting.evotingsystem.dao.VoteDao;
import com.evoting.evotingsystem.pojo.Vote;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class VoteDaoImpl implements VoteDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Vote save(Vote vote) {
        entityManager.persist(vote);
        return vote;
    }

    @Override
    public Optional<Vote> findLatestVote() {
        try {
            TypedQuery<Vote> query = entityManager.createQuery(
                    "SELECT v FROM Vote v ORDER BY v.id DESC",
                    Vote.class
            );
            query.setMaxResults(1);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public long countByCandidateId(String candidateId) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(v) FROM Vote v WHERE v.candidateId = :candidateId",
                Long.class
        );
        query.setParameter("candidateId", candidateId);
        return query.getSingleResult();
    }

    @Override
    public List<Vote> findAllOrderedById() {
        TypedQuery<Vote> query = entityManager.createQuery(
                "SELECT v FROM Vote v ORDER BY v.id ASC",
                Vote.class
        );
        return query.getResultList();
    }
}