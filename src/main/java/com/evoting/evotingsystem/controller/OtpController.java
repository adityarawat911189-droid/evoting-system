package com.evoting.evotingsystem.controller;

import com.evoting.evotingsystem.dto.RegisterVerifyResponseDto;
import com.evoting.evotingsystem.dto.SendOtpRequestDto;
import com.evoting.evotingsystem.dto.VerifyOtpRequestDto;
import com.evoting.evotingsystem.service.OtpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<RegisterVerifyResponseDto> sendOtp(
            @Valid @RequestBody SendOtpRequestDto request) {

        otpService.generateAndSendOtp(request.getEpicNumber(), request.getEmailOrMobile());

        return ResponseEntity.ok(
                new RegisterVerifyResponseDto(true, "OTP sent successfully. Check console/email.")
        );
    }

    @PostMapping("/verify")
    public ResponseEntity<RegisterVerifyResponseDto> verifyOtp(
            @Valid @RequestBody VerifyOtpRequestDto request) {

        boolean isVerified = otpService.verifyOtp(request.getEpicNumber(), request.getOtp());

        if (isVerified) {
            return ResponseEntity.ok(
                    new RegisterVerifyResponseDto(true, "OTP verified successfully.")
            );
        } else {
            return ResponseEntity.badRequest().body(
                    new RegisterVerifyResponseDto(false, "Invalid or expired OTP.")
            );
        }
    }
}