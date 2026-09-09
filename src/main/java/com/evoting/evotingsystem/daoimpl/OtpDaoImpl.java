package com.evoting.evotingsystem.daoimpl;

import com.evoting.evotingsystem.dao.OtpDao;
import com.evoting.evotingsystem.pojo.OtpVerification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class OtpDaoImpl implements OtpDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public OtpVerification save(OtpVerification otp) {
        entityManager.persist(otp);
        return otp;
    }

    @Override
    public Optional<OtpVerification> findLatestByEpicNumber(String epicNumber) {
        try {
            TypedQuery<OtpVerification> query = entityManager.createQuery(
                    "SELECT o FROM OtpVerification o WHERE o.epicNumber = :epicNumber " +
                            "ORDER BY o.createdAt DESC",
                    OtpVerification.class
            );
            query.setParameter("epicNumber", epicNumber);
            query.setMaxResults(1);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}