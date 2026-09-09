package com.evoting.evotingsystem.serviceimpl;

import com.evoting.evotingsystem.dao.OtpDao;
import com.evoting.evotingsystem.pojo.OtpVerification;
import com.evoting.evotingsystem.service.OtpService;
import com.evoting.evotingsystem.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpDao otpDao;
    @Autowired
    private JavaMailSender mailSender;

    private static final int OTP_LENGTH = 6;
    private static final int EXPIRY_MINUTES = 5;
    private static final int MAX_ATTEMPTS = 3;

    @Override
    public void generateAndSendOtp(String epicNumber, String emailOrMobile) {

        String otp = generateRandomOtp();
        String otpHash = HashUtil.sha256(otp);

        OtpVerification record = new OtpVerification();
        record.setEpicNumber(epicNumber);
        record.setOtpHash(otpHash);
        record.setAttempts(0);
        record.setExpiresAt(LocalDateTime.now().plusMinutes(EXPIRY_MINUTES));

        otpDao.save(record);

        sendOtpEmail(emailOrMobile, otp);
    }

    private void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your E-Voting OTP Verification Code");
        message.setText("Your OTP for voter registration is: " + otp +
                "\n\nThis OTP is valid for " + EXPIRY_MINUTES + " minutes." +
                "\n\nDo not share this code with anyone.");
        mailSender.send(message);
    }

    @Override
    public boolean verifyOtp(String epicNumber, String inputOtp) {

        Optional<OtpVerification> recordOpt = otpDao.findLatestByEpicNumber(epicNumber);

        if (recordOpt.isEmpty()) {
            return false;
        }

        OtpVerification record = recordOpt.get();

        if (record.getAttempts() >= MAX_ATTEMPTS) {
            return false;
        }

        if (LocalDateTime.now().isAfter(record.getExpiresAt())) {
            return false;
        }

        String inputHash = HashUtil.sha256(inputOtp);

        if (!inputHash.equals(record.getOtpHash())) {
            record.setAttempts(record.getAttempts() + 1);
            return false;
        }

        record.setVerifiedAt(LocalDateTime.now());
        return true;
    }

    private String generateRandomOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}