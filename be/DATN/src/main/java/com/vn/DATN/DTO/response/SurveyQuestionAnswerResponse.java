package com.vn.DATN.DTO.response;

import java.time.LocalDate;

public interface SurveyQuestionAnswerResponse {
    Integer getSurveyId();
    String getSurveyTitle();
    String getDescription();
    LocalDate getDeadline();
    String getCourseName();

    Integer getQuestionId();
    String getQuestionText();
    String getQuestionType();

    Integer getAnswerId();
    String getAnswerContent();
    Integer getAnswerPoint();
}
