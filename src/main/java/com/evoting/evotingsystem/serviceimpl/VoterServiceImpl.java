package com.evoting.evotingsystem.serviceimpl;

import com.evoting.evotingsystem.dao.VoterDao;
import com.evoting.evotingsystem.pojo.Voter;
import com.evoting.evotingsystem.service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HexFormat;
import java.util.Optional;

@Service
public class VoterServiceImpl implements VoterService {

    @Autowired
    private VoterDao voterDao;

    @Override
    public boolean validateLogin(String epicNumber, String rawPassword) {

        Optional<Voter> voterOpt = voterDao.findByEpicNumber(epicNumber);

        if (voterOpt.isEmpty()) {
            return false;
        }

        Voter voter = voterOpt.get();

        if (voter.getPasswordHash() == null) {
            return false;
        }

        String inputHash = hashValue(rawPassword);

        return inputHash.equals(voter.getPasswordHash());
    }

    @Override
    public String getConstituencyId(String epicNumber) {
        Optional<Voter> voterOpt = voterDao.findByEpicNumber(epicNumber);
        return voterOpt.map(Voter::getConstituencyId).orElse(null);
    }

    @Override
    public boolean verifyVoterIdentity(String epicNumber, String aadhaarNumber, String dateOfBirth) {

        String aadhaarHash = hashValue(aadhaarNumber);

        Optional<Voter> voterOpt = voterDao.findByEpicNumberAndAadhaarHash(epicNumber, aadhaarHash);

        if (voterOpt.isEmpty()) {
            return false;
        }

        Voter voter = voterOpt.get();

        LocalDate submittedDob = LocalDate.parse(dateOfBirth);
        if (!voter.getDateOfBirth().equals(submittedDob)) {
            return false;
        }

        if (!voter.getIsEligible()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean setPasswordAndPin(String epicNumber, String rawPassword, String rawPin) {

        Optional<Voter> voterOpt = voterDao.findByEpicNumber(epicNumber);

        if (voterOpt.isEmpty()) {
            return false;
        }

        Voter voter = voterOpt.get();

        String passwordHash = hashValue(rawPassword);
        String pinHash = hashValue(rawPin);

        voter.setPasswordHash(passwordHash);
        voter.setPinHash(pinHash);

        voterDao.save(voter);

        return true;
    }

    private String hashValue(String rawValue) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(rawValue.getBytes());
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }
}