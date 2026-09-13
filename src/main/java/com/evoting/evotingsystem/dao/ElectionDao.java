package com.evoting.evotingsystem.dao;

import com.evoting.evotingsystem.pojo.Election;
import java.util.Optional;

public interface ElectionDao {

    Election save(Election election);

    Optional<Election> findByConstituencyId(String constituencyId);

    boolean updateStatus(String constituencyId, String oldStatus, String newStatus);
}