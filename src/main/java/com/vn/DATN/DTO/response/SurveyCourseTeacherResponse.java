package com.vn.DATN.DTO.response;

import java.time.LocalDate;

public interface SurveyCourseTeacherResponse {
    Integer getSurveyId();
    String getSurveyTitle();
    String getCourseName();
    String getTeacherName();
    LocalDate getDeadline();
    Boolean getIsSubmitted();
}
