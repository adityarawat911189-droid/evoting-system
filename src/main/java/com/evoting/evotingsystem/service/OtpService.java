package com.evoting.evotingsystem.service;

public interface OtpService {

    void generateAndSendOtp(String epicNumber, String emailOrMobile);

    boolean verifyOtp(String epicNumber, String inputOtp);
}