package com.evoting.evotingsystem.serviceimpl;

import com.evoting.evotingsystem.dao.ElectionDao;
import com.evoting.evotingsystem.pojo.Election;
import com.evoting.evotingsystem.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ElectionServiceImpl implements ElectionService {

    @Autowired
    private ElectionDao electionDao;

    @Override
    public void createElection(String electionId, String constituencyId) {
        if (electionDao.findByConstituencyId(constituencyId).isPresent()) {
            return;
        }
        Election election = new Election();
        election.setElectionId(electionId);
        election.setConstituencyId(constituencyId);
        election.setStatus("NOT_STARTED");
        electionDao.save(election);
    }

    @Override
    public boolean startElection(String constituencyId) {
        return electionDao.updateStatus(constituencyId, "NOT_STARTED", "ONGOING");
    }

    @Override
    public boolean closeElection(String constituencyId) {
        return electionDao.updateStatus(constituencyId, "ONGOING", "CLOSED");
    }

    @Override
    public String getElectionStatus(String constituencyId) {
        Optional<Election> electionOpt = electionDao.findByConstituencyId(constituencyId);
        return electionOpt.map(Election::getStatus).orElse("NOT_FOUND");
    }
}