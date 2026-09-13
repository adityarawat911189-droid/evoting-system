package com.evoting.evotingsystem.controller;

import com.evoting.evotingsystem.service.FaceVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/face")
public class FaceVerificationController {

    @Autowired
    private FaceVerificationService faceVerificationService;

    @PostMapping(value = "/verify", consumes = "multipart/form-data")
    public ResponseEntity<String> verifyFace(
            @RequestParam("liveImage") MultipartFile liveImage,
            @RequestParam("referenceImage") MultipartFile referenceImage) {

        try {
            boolean isMatch = faceVerificationService.verifyFaceMatch(liveImage, referenceImage);

            if (isMatch) {
                return ResponseEntity.ok("Face verified successfully. Match confirmed.");
            } else {
                return ResponseEntity.badRequest().body("Face verification failed. Faces do not match.");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing images: " + e.getMessage());
        }
    }
}