package com.evoting.evotingsystem.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FaceVerificationService {

    boolean verifyFaceMatch(MultipartFile liveImage, MultipartFile referenceImage) throws IOException;

}