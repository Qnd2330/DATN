package com.vn.DATN.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletedSurveyFilterDTO {
    private String userId;
    private String userName;
    private String facultyName;
    private String className;
    private String surveyTitle;
    private String submitTimeFrom;
    private String submitTimeTo;
    private Integer page;
    private Integer size;
} 