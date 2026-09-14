package com.evoting.evotingsystem.controller;

import com.evoting.evotingsystem.dao.CandidateDao;
import com.evoting.evotingsystem.dto.CandidateRequestDto;
import com.evoting.evotingsystem.pojo.Candidate;
import com.evoting.evotingsystem.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateDao candidateDao;

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

    @PostMapping("/add")
    public ResponseEntity<String> addCandidate(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @Valid @RequestBody CandidateRequestDto request) {

        if (!isValidAdmin(authHeader)) {
            return ResponseEntity.status(403).body("Admin access required.");
        }

        Candidate candidate = new Candidate();
        candidate.setCandidateId(request.getCandidateId());
        candidate.setName(request.getName());
        candidate.setParty(request.getParty());
        candidate.setConstituencyId(request.getConstituencyId());
        candidate.setManifesto(request.getManifesto());

        candidateDao.save(candidate);

        return ResponseEntity.ok("Candidate added successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> getCandidatesByConstituency(
            @RequestParam String constituencyId) {

        List<Candidate> candidates = candidateDao.findByConstituencyId(constituencyId);
        return ResponseEntity.ok(candidates);
    }
}