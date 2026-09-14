package com.evoting.evotingsystem.controller;

import com.evoting.evotingsystem.service.ElectionService;
import com.evoting.evotingsystem.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/election")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @Autowired
    private JwtUtil jwtUtil;

    private boolean isValidAdmin(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }
        String token = authHeader.replace("Bearer ", "");
        if (!jwtUtil.isTokenValid(token)) {
            return false;
        }
        String subject = jwtUtil.extractEpicNumber(token);
        return subject != null && subject.startsWith("ADMIN_");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createElection(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam String electionId,
            @RequestParam String constituencyId) {

        if (!isValidAdmin(authHeader)) {
            return ResponseEntity.status(403).body("Admin access required.");
        }

        electionService.createElection(electionId, constituencyId);
        return ResponseEntity.ok("Election created with status NOT_STARTED.");
    }

    @PostMapping("/start")
    public ResponseEntity<String> startElection(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam String constituencyId) {

        if (!isValidAdmin(authHeader)) {
            return ResponseEntity.status(403).body("Admin access required.");
        }

        boolean success = electionService.startElection(constituencyId);

        if (success) {
            return ResponseEntity.ok("Election started. Status: ONGOING.");
        } else {
            return ResponseEntity.badRequest().body("Could not start election. Check current status.");
        }
    }

    @PostMapping("/close")
    public ResponseEntity<String> closeElection(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam String constituencyId) {

        if (!isValidAdmin(authHeader)) {
            return ResponseEntity.status(403).body("Admin access required.");
        }

        boolean success = electionService.closeElection(constituencyId);

        if (success) {
            return ResponseEntity.ok("Election closed. Status: CLOSED.");
        } else {
            return ResponseEntity.badRequest().body("Could not close election. Check current status.");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus(@RequestParam String constituencyId) {
        String status = electionService.getElectionStatus(constituencyId);
        return ResponseEntity.ok(status);
    }
}