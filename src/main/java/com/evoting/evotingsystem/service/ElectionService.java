package com.evoting.evotingsystem.service;

public interface ElectionService {

    void createElection(String electionId, String constituencyId);

    boolean startElection(String constituencyId);

    boolean closeElection(String constituencyId);

    String getElectionStatus(String constituencyId);

}