package com.evoting.evotingsystem.serviceimpl;

import com.evoting.evotingsystem.dao.VotingTokenDao;
import com.evoting.evotingsystem.pojo.VotingToken;
import com.evoting.evotingsystem.service.VotingTokenService;
import com.evoting.evotingsystem.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class VotingTokenServiceImpl implements VotingTokenService {

    @Autowired
    private VotingTokenDao votingTokenDao;

    private static final int TOKEN_VALIDITY_MINUTES = 10;

    @Override
    public String issueToken(String epicNumber) {

        String rawToken = generateRawToken();
        String tokenHash = HashUtil.sha256(rawToken);

        VotingToken token = new VotingToken();
        token.setTokenHash(tokenHash);
        token.setEpicNumber(epicNumber);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(TOKEN_VALIDITY_MINUTES));
        token.setIsUsed(false);

        votingTokenDao.save(token);

        return rawToken;
    }

    private String generateRawToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}