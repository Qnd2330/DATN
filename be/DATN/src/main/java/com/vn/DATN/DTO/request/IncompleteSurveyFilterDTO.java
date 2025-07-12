package com.vn.DATN.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncompleteSurveyFilterDTO {
    private String userId;
    private String userName;
    private String facultyName;
    private String className;
    private String surveyName;
    private String deadlineFrom;
    private String deadlineTo;
    private Integer page;
    private Integer size;
} 