package com.vn.DATN.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncompleteSurveyResponse {
    private Long userId;
    private String userName;
    private String surveyName;
    private String facultyName;
    private String className;
    private String deadline;
} 