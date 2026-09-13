package com.evoting.evotingsystem.serviceimpl;

import com.evoting.evotingsystem.service.FaceVerificationService;
import org.bytedeco.javacv.Java2DFrameUtils;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_core.*;

@Service
public class FaceVerificationServiceImpl implements FaceVerificationService {

    private CascadeClassifier faceDetector;

    private CascadeClassifier getFaceDetector() throws IOException {
        if (faceDetector == null) {
            ClassPathResource resource = new ClassPathResource("haarcascade_frontalface_default.xml");
            java.io.File tempFile = java.io.File.createTempFile("haarcascade", ".xml");
            tempFile.deleteOnExit();
            try (InputStream in = resource.getInputStream();
                 java.io.FileOutputStream out = new java.io.FileOutputStream(tempFile)) {
                in.transferTo(out);
            }
            faceDetector = new CascadeClassifier(tempFile.getAbsolutePath());
        }
        return faceDetector;
    }

    @Override
    public boolean verifyFaceMatch(MultipartFile liveImage, MultipartFile referenceImage) throws IOException {

        Mat liveFace = detectAndCropFace(liveImage);
        Mat referenceFace = detectAndCropFace(referenceImage);

        if (liveFace == null || referenceFace == null) {
            return false;
        }

        double similarity = compareFaces(liveFace, referenceFace);

        return similarity > 0.75;
    }

    private Mat detectAndCropFace(MultipartFile file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        Mat mat = Java2DFrameUtils.toMat(bufferedImage);

        Mat gray = new Mat();
        cvtColor(mat, gray, COLOR_BGR2GRAY);

        RectVector faces = new RectVector();
        getFaceDetector().detectMultiScale(gray, faces);

        if (faces.size() == 0) {
            return null;
        }

        Rect faceRect = faces.get(0);
        Mat faceRegion = new Mat(gray, faceRect);

        Mat resized = new Mat();
        resize(faceRegion, resized, new Size(200, 200));

        return resized;
    }

    private double compareFaces(Mat face1, Mat face2) {
        Mat diff = new Mat();
        absdiff(face1, face2, diff);

        Scalar meanDiff = mean(diff);
        double avgPixelDifference = meanDiff.get(0);

        double similarity = 1.0 - (avgPixelDifference / 255.0);

        return similarity;
    }
}