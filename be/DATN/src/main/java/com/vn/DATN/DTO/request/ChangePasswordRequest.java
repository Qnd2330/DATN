package com.vn.DATN.DTO.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private Integer userId;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String otp;
}
