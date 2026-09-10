package com.evoting.evotingsystem.controller;

import com.evoting.evotingsystem.service.VotingTokenService;
import com.evoting.evotingsystem.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vote-token")
public class VotingTokenController {

    @Autowired
    private VotingTokenService votingTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/issue")
    public ResponseEntity<String> issueToken(@RequestHeader("Authorization") String authHeader) {

        String jwtToken = authHeader.replace("Bearer ", "");

        if (!jwtUtil.isTokenValid(jwtToken)) {
            return ResponseEntity.status(401).body("Invalid or expired login session.");
        }

        String epicNumber = jwtUtil.extractEpicNumber(jwtToken);
        String votingToken = votingTokenService.issueToken(epicNumber);

        return ResponseEntity.ok(votingToken);
    }
}