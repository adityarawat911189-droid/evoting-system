package com.evoting.evotingsystem.controller;

import com.evoting.evotingsystem.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/election")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @PostMapping("/create")
    public ResponseEntity<String> createElection(
            @RequestParam String electionId,
            @RequestParam String constituencyId) {

        electionService.createElection(electionId, constituencyId);
        return ResponseEntity.ok("Election created with status NOT_STARTED.");
    }

    @PostMapping("/start")
    public ResponseEntity<String> startElection(@RequestParam String constituencyId) {
        boolean success = electionService.startElection(constituencyId);

        if (success) {
            return ResponseEntity.ok("Election started. Status: ONGOING.");
        } else {
            return ResponseEntity.badRequest().body("Could not start election. Check current status.");
        }
    }

    @PostMapping("/close")
    public ResponseEntity<String> closeElection(@RequestParam String constituencyId) {
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