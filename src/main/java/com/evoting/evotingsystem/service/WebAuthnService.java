package com.evoting.evotingsystem.service;

public interface WebAuthnService {

    String generateRegistrationChallenge(String epicNumber);

    boolean verifyRegistration(String epicNumber, String credentialId, String attestationObject, String clientDataJSON);

    String generateAuthenticationChallenge(String epicNumber);

    boolean verifyAuthentication(String epicNumber, String credentialId, String authenticatorData, String clientDataJSON, String signature);

}