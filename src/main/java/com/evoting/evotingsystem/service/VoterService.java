package com.evoting.evotingsystem.service;

public interface VoterService {

    boolean verifyVoterIdentity(String epicNumber, String aadhaarNumber, String dateOfBirth);

    boolean setPasswordAndPin(String epicNumber, String rawPassword, String rawPin);

    boolean validateLogin(String epicNumber, String rawPassword);

    String getConstituencyId(String epicNumber);

}