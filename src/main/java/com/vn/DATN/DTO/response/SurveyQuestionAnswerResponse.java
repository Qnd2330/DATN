package com.vn.DATN.DTO.response;

public interface SurveyQuestionAnswerResponse {
    Integer getSurveyId();
    String getSurveyTitle();
    String getDescription();
    String getCourseName();

    Integer getQuestionId();
    String getQuestionText();
    String getQuestionType();

    Integer getAnswerId();
    String getAnswerContent();
    Integer getAnswerPoint();
}
