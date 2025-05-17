package com.vn.DATN.DTO.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StudentDTO {
    private int studentId;
    private String studentName;
    private String phone;
    private String email;
}
