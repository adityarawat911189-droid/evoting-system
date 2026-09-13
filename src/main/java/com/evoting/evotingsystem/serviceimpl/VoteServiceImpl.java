package com.evoting.evotingsystem.serviceimpl;

import com.evoting.evotingsystem.dao.VoteDao;
import com.evoting.evotingsystem.dao.VoterDao;
import com.evoting.evotingsystem.dao.VotingTokenDao;
import com.evoting.evotingsystem.pojo.Vote;
import com.evoting.evotingsystem.pojo.Voter;
import com.evoting.evotingsystem.pojo.VotingToken;
import com.evoting.evotingsystem.service.VoteService;
import com.evoting.evotingsystem.util.HashUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VotingTokenDao votingTokenDao;

    @Autowired
    private VoterDao voterDao;

    @Autowired
    private VoteDao voteDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public String castVote(String rawToken, String candidateId, String pin) {

        String tokenHash = HashUtil.sha256(rawToken);

        Optional<VotingToken> tokenOpt = votingTokenDao.findByTokenHash(tokenHash);

        if (tokenOpt.isEmpty()) {
            return "Invalid token.";
        }

        VotingToken token = tokenOpt.get();

        if (Boolean.TRUE.equals(token.getIsUsed())) {
            return "This token has already been used.";
        }

        if (LocalDateTime.now().isAfter(token.getExpiresAt())) {
            return "Token has expired. Please request a new one.";
        }

        String epicNumber = token.getEpicNumber();

        Optional<Voter> voterOpt = voterDao.findByEpicNumber(epicNumber);
        if (voterOpt.isEmpty()) {
            return "Voter record not found.";
        }

        Voter voter = voterOpt.get();

        if (Boolean.TRUE.equals(voter.getHasVoted())) {
            return "You have already voted.";
        }

        String pinHash = HashUtil.sha256(pin);
        if (!pinHash.equals(voter.getPinHash())) {
            return "Incorrect PIN.";
        }

        boolean tokenMarked = votingTokenDao.markTokenAsUsed(tokenHash);
        if (!tokenMarked) {
            return "This token has already been used.";
        }

        boolean voteMarked = markVoterAsVoted(epicNumber);
        if (!voteMarked) {
            return "You have already voted.";
        }

        String previousHash = voteDao.findLatestVote()
                .map(Vote::getVoteHash)
                .orElse("GENESIS");

        String voteHash = HashUtil.sha256(candidateId + previousHash + System.nanoTime());

        Vote vote = new Vote();
        vote.setCandidateId(candidateId);
        vote.setConstituencyId(voter.getConstituencyId());
        vote.setVoteHash(voteHash);
        vote.setPreviousHash(previousHash);

        voteDao.save(vote);

        votingTokenDao.deleteToken(tokenHash);

        return "Vote cast successfully.";
    }

    private boolean markVoterAsVoted(String epicNumber) {
        Query query = entityManager.createQuery(
                "UPDATE Voter v SET v.hasVoted = true WHERE v.epicNumber = :epicNumber AND v.hasVoted = false"
        );
        query.setParameter("epicNumber", epicNumber);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean verifyLedgerIntegrity() {

        java.util.List<Vote> allVotes = voteDao.findAllOrderedById();

        String expectedPreviousHash = "GENESIS";

        for (Vote vote : allVotes) {
            if (!vote.getPreviousHash().equals(expectedPreviousHash)) {
                return false;
            }
            expectedPreviousHash = vote.getVoteHash();
        }

        return true;
    }
}