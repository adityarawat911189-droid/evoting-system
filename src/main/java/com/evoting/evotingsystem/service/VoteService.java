package com.evoting.evotingsystem.service;

public interface VoteService {

    String castVote(String rawToken, String candidateId, String pin);

}