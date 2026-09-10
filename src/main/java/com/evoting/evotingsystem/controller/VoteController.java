package com.evoting.evotingsystem.controller;

import com.evoting.evotingsystem.dto.VoteRequestDto;
import com.evoting.evotingsystem.service.VoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("/cast")
    public ResponseEntity<String> castVote(@Valid @RequestBody VoteRequestDto request) {

        String result = voteService.castVote(
                request.getToken(),
                request.getCandidateId(),
                request.getPin()
        );

        if (result.equals("Vote cast successfully.")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}