package com.evoting.evotingsystem.serviceimpl;

import com.evoting.evotingsystem.dao.CandidateDao;
import com.evoting.evotingsystem.dao.VoteDao;
import com.evoting.evotingsystem.dto.CandidateResultDto;
import com.evoting.evotingsystem.pojo.Candidate;
import com.evoting.evotingsystem.service.ElectionService;
import com.evoting.evotingsystem.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private VoteDao voteDao;

    @Autowired
    private ElectionService electionService;

    @Override
    public List<CandidateResultDto> getResults(String constituencyId) {

        String status = electionService.getElectionStatus(constituencyId);

        if (!"CLOSED".equals(status)) {
            throw new IllegalStateException("Results are only available after the election is closed. Current status: " + status);
        }

        List<Candidate> candidates = candidateDao.findByConstituencyId(constituencyId);
        List<CandidateResultDto> results = new ArrayList<>();

        for (Candidate candidate : candidates) {
            long count = voteDao.countByCandidateId(candidate.getCandidateId());
            results.add(new CandidateResultDto(
                    candidate.getCandidateId(),
                    candidate.getName(),
                    count
            ));
        }

        return results;
    }
}