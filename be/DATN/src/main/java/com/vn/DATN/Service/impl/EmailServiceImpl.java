package com.vn.DATN.Service.impl;

import com.vn.DATN.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    @Override
    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Mã xác nhận đổi mật khẩu");
        message.setText("Mã xác nhận của bạn là: " + otp + ". Mã sẽ hết hạn sau 5 phút.");

        mailSender.send(message);
    }
}
