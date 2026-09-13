package com.evoting.evotingsystem.daoimpl;

import com.evoting.evotingsystem.dao.ElectionDao;
import com.evoting.evotingsystem.pojo.Election;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class ElectionDaoImpl implements ElectionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Election save(Election election) {
        entityManager.persist(election);
        return election;
    }

    @Override
    public Optional<Election> findByConstituencyId(String constituencyId) {
        try {
            TypedQuery<Election> query = entityManager.createQuery(
                    "SELECT e FROM Election e WHERE e.constituencyId = :constituencyId",
                    Election.class
            );
            query.setParameter("constituencyId", constituencyId);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public boolean updateStatus(String constituencyId, String oldStatus, String newStatus) {
        Query query = entityManager.createQuery(
                "UPDATE Election e SET e.status = :newStatus " +
                        "WHERE e.constituencyId = :constituencyId AND e.status = :oldStatus"
        );
        query.setParameter("newStatus", newStatus);
        query.setParameter("constituencyId", constituencyId);
        query.setParameter("oldStatus", oldStatus);

        return query.executeUpdate() > 0;
    }
}