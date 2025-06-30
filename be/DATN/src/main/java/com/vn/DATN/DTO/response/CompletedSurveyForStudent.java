package com.vn.DATN.DTO.response;

import java.time.LocalDateTime;

public interface CompletedSurveyForStudent {
    Integer getSurveyResultId();
    Integer getSurveyId();
    String getSurveyTitle();

    LocalDateTime getCreateAt();
    LocalDateTime getUpdatedAt();

    Integer getCourseId();
    String getCourseName();
}
