package com.evoting.evotingsystem.controller;
import com.evoting.evotingsystem.dto.SetCredentialsRequestDto;
import com.evoting.evotingsystem.dto.RegisterVerifyRequestDto;
import com.evoting.evotingsystem.dto.RegisterVerifyResponseDto;
import com.evoting.evotingsystem.service.VoterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private VoterService voterService;

    @PostMapping("/set-credentials")
    public ResponseEntity<RegisterVerifyResponseDto> setCredentials(
            @Valid @RequestBody SetCredentialsRequestDto request) {

        boolean success = voterService.setPasswordAndPin(
                request.getEpicNumber(),
                request.getPassword(),
                request.getPin()
        );

        if (success) {
            return ResponseEntity.ok(
                    new RegisterVerifyResponseDto(true, "Password and PIN set successfully. Registration complete.")
            );
        } else {
            return ResponseEntity.badRequest().body(
                    new RegisterVerifyResponseDto(false, "Voter not found.")
            );
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<RegisterVerifyResponseDto> verifyVoter(
            @Valid @RequestBody RegisterVerifyRequestDto request) {

        boolean isVerified = voterService.verifyVoterIdentity(
                request.getEpicNumber(),
                request.getAadhaarNumber(),
                request.getDateOfBirth()
        );

        if (isVerified) {
            return ResponseEntity.ok(
                    new RegisterVerifyResponseDto(true, "Identity verified. Proceed to OTP verification.")
            );
        } else {
            return ResponseEntity.badRequest().body(
                    new RegisterVerifyResponseDto(false, "Verification failed. Details do not match our records.")
            );
        }
    }
}