package com.vn.DATN.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchFilterDTO {
    private String userName;
    private String email;
    private String phoneNumber;
    private String birthDate;
    private String gender;
    private String role;
    private String facultyName;
    private String className;
} 