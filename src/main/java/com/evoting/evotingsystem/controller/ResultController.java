package com.evoting.evotingsystem.controller;

import com.evoting.evotingsystem.dto.CandidateResultDto;
import com.evoting.evotingsystem.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping
    public ResponseEntity<?> getResults(@RequestParam String constituencyId) {
        try {
            List<CandidateResultDto> results = resultService.getResults(constituencyId);
            return ResponseEntity.ok(results);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}