package com.vn.DATN.Service.impl;

import com.vn.DATN.Service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> otpExpireTime = new ConcurrentHashMap<>();

    @Override
    public String generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(email, otp);
        otpExpireTime.put(email, LocalDateTime.now().plusMinutes(5));
        return otp;
    }

    @Override
    public boolean validateOtp(String email, String otp) {
        return otp.equals(otpStorage.get(email)) &&
                LocalDateTime.now().isBefore(otpExpireTime.get(email));
    }

    @Override
    public void clearOtp(String email) {
        otpStorage.remove(email);
        otpExpireTime.remove(email);
    }
}
