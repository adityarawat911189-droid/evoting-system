package com.evoting.evotingsystem.service;

public interface VoteService {

    String castVote(String rawToken, String candidateId, String pin,
                    String epicNumberForFingerprint, String credentialId,
                    String authenticatorData, String clientDataJSON, String signature);

    boolean verifyLedgerIntegrity();

}