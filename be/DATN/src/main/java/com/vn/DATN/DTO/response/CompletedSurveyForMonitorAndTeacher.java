package com.vn.DATN.DTO.response;

import java.time.LocalDateTime;

public interface CompletedSurveyForMonitorAndTeacher {
    Integer getSurveyResultId();
    Integer getSurveyId();
    String getSurveyTitle();

    Integer getUserId();
    String getStudentName();

    Integer getEvaluatorId();
    String getEvaluatorName();

    Integer getScore();
    LocalDateTime getCreateAt();
    LocalDateTime getUpdatedAt();

    Integer getCourseId();
    String getCourseName();
}
