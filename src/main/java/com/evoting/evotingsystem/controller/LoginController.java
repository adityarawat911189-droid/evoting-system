package com.evoting.evotingsystem.controller;

import com.evoting.evotingsystem.dto.LoginRequestDto;
import com.evoting.evotingsystem.dto.LoginResponseDto;
import com.evoting.evotingsystem.service.VoterService;
import com.evoting.evotingsystem.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private VoterService voterService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto request) {

        boolean isValid = voterService.validateLogin(
                request.getEpicNumber(),
                request.getPassword()
        );

        if (isValid) {
            String token = jwtUtil.generateToken(request.getEpicNumber());
            return ResponseEntity.ok(
                    new LoginResponseDto(true, "Login successful.", token)
            );
        } else {
            return ResponseEntity.status(401).body(
                    new LoginResponseDto(false, "Invalid EPIC number or password.", null)
            );
        }
    }
}