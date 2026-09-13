package com.evoting.evotingsystem.dao;

import com.evoting.evotingsystem.pojo.WebAuthnCredential;
import java.util.List;
import java.util.Optional;

public interface WebAuthnCredentialDao {

    WebAuthnCredential save(WebAuthnCredential credential);

    List<WebAuthnCredential> findByEpicNumber(String epicNumber);

    Optional<WebAuthnCredential> findByCredentialId(String credentialId);
}