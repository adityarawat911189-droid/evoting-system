package com.evoting.evotingsystem.dao;

import com.evoting.evotingsystem.pojo.OtpVerification;
import java.util.Optional;

public interface OtpDao {

    OtpVerification save(OtpVerification otp);

    Optional<OtpVerification> findLatestByEpicNumber(String epicNumber);
}