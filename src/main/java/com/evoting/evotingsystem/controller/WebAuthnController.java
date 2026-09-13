package com.evoting.evotingsystem.controller;

import com.evoting.evotingsystem.dto.WebAuthnRegisterVerifyDto;
import com.evoting.evotingsystem.service.WebAuthnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/webauthn")
public class WebAuthnController {

    @Autowired
    private WebAuthnService webAuthnService;

    @GetMapping("/register/options")
    public ResponseEntity<Map<String, Object>> getRegistrationOptions(@RequestParam String epicNumber) {

        String challenge = webAuthnService.generateRegistrationChallenge(epicNumber);

        Map<String, Object> options = new HashMap<>();
        options.put("challenge", challenge);
        options.put("epicNumber", epicNumber);
        options.put("rpName", "E-Voting System");
        options.put("rpId", "localhost");
        options.put("userName", epicNumber);

        return ResponseEntity.ok(options);
    }

    @PostMapping("/register/verify")
    public ResponseEntity<String> verifyRegistration(@RequestBody WebAuthnRegisterVerifyDto request) {

        boolean success = webAuthnService.verifyRegistration(
                request.getEpicNumber(),
                request.getCredentialId(),
                request.getAttestationObject(),
                request.getClientDataJSON()
        );

        if (success) {
            return ResponseEntity.ok("Fingerprint registered successfully.");
        } else {
            return ResponseEntity.badRequest().body("Fingerprint registration failed.");
        }
    }
    @GetMapping("/authenticate/options")
    public ResponseEntity<Map<String, Object>> getAuthenticationOptions(@RequestParam String epicNumber) {

        String challenge = webAuthnService.generateAuthenticationChallenge(epicNumber);

        Map<String, Object> options = new HashMap<>();
        options.put("challenge", challenge);
        options.put("rpId", "localhost");

        return ResponseEntity.ok(options);
    }

    @PostMapping("/authenticate/verify")
    public ResponseEntity<String> verifyAuthentication(@RequestBody Map<String, String> request) {

        boolean success = webAuthnService.verifyAuthentication(
                request.get("epicNumber"),
                request.get("credentialId"),
                request.get("authenticatorData"),
                request.get("clientDataJSON"),
                request.get("signature")
        );

        if (success) {
            return ResponseEntity.ok("Fingerprint authentication successful.");
        } else {
            return ResponseEntity.badRequest().body("Fingerprint authentication failed.");
        }
    }
}