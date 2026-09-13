package com.evoting.evotingsystem.serviceimpl;

import com.webauthn4j.data.attestation.authenticator.AAGUID;
import com.evoting.evotingsystem.dao.WebAuthnChallengeDao;
import com.evoting.evotingsystem.dao.WebAuthnCredentialDao;
import com.evoting.evotingsystem.pojo.WebAuthnChallenge;
import com.evoting.evotingsystem.pojo.WebAuthnCredential;
import com.evoting.evotingsystem.service.WebAuthnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.webauthn4j.data.AuthenticationRequest;
import com.webauthn4j.data.AuthenticationParameters;
import com.webauthn4j.data.AuthenticationData;
import com.webauthn4j.authenticator.Authenticator;
import com.webauthn4j.authenticator.AuthenticatorImpl;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.attestation.authenticator.COSEKey;
import java.security.SecureRandom;
import java.util.Base64;
import com.webauthn4j.WebAuthnManager;
import com.webauthn4j.data.*;
import com.webauthn4j.data.client.Origin;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.server.ServerProperty;
import com.webauthn4j.util.Base64UrlUtil;

@Service
public class WebAuthnServiceImpl implements WebAuthnService {

    @Autowired
    private WebAuthnChallengeDao challengeDao;

    @Autowired
    private WebAuthnCredentialDao credentialDao;

    @Override
    public String generateRegistrationChallenge(String epicNumber) {
        String challengeValue = generateRandomChallenge();

        WebAuthnChallenge challenge = new WebAuthnChallenge();
        challenge.setEpicNumber(epicNumber);
        challenge.setChallenge(challengeValue);

        challengeDao.save(challenge);

        return challengeValue;
    }

    private String generateRandomChallenge() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Override
    public boolean verifyRegistration(String epicNumber, String credentialId, String attestationObject, String clientDataJSON) {

        try {
            var challengeOpt = challengeDao.findByEpicNumber(epicNumber);
            if (challengeOpt.isEmpty()) {
                return false;
            }

            String expectedChallengeValue = challengeOpt.get().getChallenge();
            Challenge expectedChallenge = new DefaultChallenge(expectedChallengeValue);

            Origin origin = new Origin("http://localhost:8080");
            String rpId = "localhost";

            ServerProperty serverProperty = new ServerProperty(origin, rpId, expectedChallenge);

            WebAuthnManager webAuthnManager = WebAuthnManager.createNonStrictWebAuthnManager();

            RegistrationRequest registrationRequest = new RegistrationRequest(
                    Base64UrlUtil.decode(attestationObject),
                    Base64UrlUtil.decode(clientDataJSON)
            );

            RegistrationParameters registrationParameters = new RegistrationParameters(
                    serverProperty,
                    false
            );

            RegistrationData registrationData = webAuthnManager.parse(registrationRequest);
            webAuthnManager.validate(registrationData, registrationParameters);

            WebAuthnCredential credential = new WebAuthnCredential();
            credential.setCredentialId(credentialId);
            credential.setEpicNumber(epicNumber);
            com.webauthn4j.data.attestation.authenticator.COSEKey coseKeyToStore =
                    registrationData.getAttestationObject().getAuthenticatorData().getAttestedCredentialData().getCOSEKey();

            ObjectConverter objectConverter = new ObjectConverter();
            byte[] coseKeyBytes = objectConverter.getCborConverter().writeValueAsBytes(coseKeyToStore);

            credential.setPublicKeyCose(Base64UrlUtil.encodeToString(coseKeyBytes));
            credential.setSignCount(registrationData.getAttestationObject().getAuthenticatorData().getSignCount());

            credentialDao.save(credential);

            challengeDao.deleteByEpicNumber(epicNumber);

            return true;

        } catch (Exception e) {
            System.out.println("WebAuthn registration verification failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String generateAuthenticationChallenge(String epicNumber) {
        return generateRegistrationChallenge(epicNumber);
    }

    @Override
    public boolean verifyAuthentication(String epicNumber, String credentialId, String authenticatorData, String clientDataJSON, String signature) {

        try {
            var challengeOpt = challengeDao.findByEpicNumber(epicNumber);
            if (challengeOpt.isEmpty()) {
                return false;
            }

            var storedCredentialOpt = credentialDao.findByCredentialId(credentialId);
            if (storedCredentialOpt.isEmpty()) {
                return false;
            }

            var storedCredential = storedCredentialOpt.get();

            if (!storedCredential.getEpicNumber().equals(epicNumber)) {
                return false;
            }

            String expectedChallengeValue = challengeOpt.get().getChallenge();
            Challenge expectedChallenge = new DefaultChallenge(expectedChallengeValue);

            Origin origin = new Origin("http://localhost:8080");
            String rpId = "localhost";

            ServerProperty serverProperty = new ServerProperty(origin, rpId, expectedChallenge);

            ObjectConverter objectConverter = new ObjectConverter();
            COSEKey coseKey = objectConverter.getCborConverter().readValue(
                    Base64UrlUtil.decode(storedCredential.getPublicKeyCose()),
                    COSEKey.class
            );

            Authenticator authenticator = new AuthenticatorImpl(
                    new com.webauthn4j.data.attestation.authenticator.AttestedCredentialData(
                            new AAGUID(new byte[16]), Base64UrlUtil.decode(credentialId), coseKey
                    ),
                    null,
                    storedCredential.getSignCount()
            );

            WebAuthnManager webAuthnManager = WebAuthnManager.createNonStrictWebAuthnManager();

            AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                    Base64UrlUtil.decode(credentialId),
                    Base64UrlUtil.decode(authenticatorData),
                    Base64UrlUtil.decode(clientDataJSON),
                    Base64UrlUtil.decode(signature)
            );

            AuthenticationParameters authenticationParameters = new AuthenticationParameters(
                    serverProperty,
                    authenticator,
                    false
            );

            AuthenticationData authenticationData = webAuthnManager.parse(authenticationRequest);
            webAuthnManager.validate(authenticationData, authenticationParameters);

            storedCredential.setSignCount(authenticationData.getAuthenticatorData().getSignCount());
            credentialDao.save(storedCredential);

            challengeDao.deleteByEpicNumber(epicNumber);

            return true;

        } catch (Exception e) {
            System.out.println("WebAuthn authentication verification failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}