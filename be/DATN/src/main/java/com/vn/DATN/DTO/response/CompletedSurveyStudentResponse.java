package com.vn.DATN.DTO.response;

import java.time.LocalDateTime;

public interface CompletedSurveyStudentResponse {
    Long getUserId();
    String getUserName();
    String getFacultyName();
    String getClassName();
    String getSurveyTitle();
    LocalDateTime getSubmitTime();
} 