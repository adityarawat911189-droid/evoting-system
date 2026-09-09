package com.evoting.evotingsystem.service;

public interface VoterService {

    boolean verifyVoterIdentity(String epicNumber, String aadhaarNumber, String dateOfBirth);

}