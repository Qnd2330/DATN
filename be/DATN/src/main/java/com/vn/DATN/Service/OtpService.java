package com.vn.DATN.Service;

public interface OtpService {

    String generateOtp(String email);

    boolean validateOtp(String email, String otp);

    void clearOtp(String email);
}
