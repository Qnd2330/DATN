package com.vn.DATN.DTO.request;

import lombok.Data;

@Data
public class LoginRequest {
    private Integer userId;
    private String password;
}
