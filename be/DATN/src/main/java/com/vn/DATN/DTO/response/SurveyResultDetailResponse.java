package com.vn.DATN.DTO.response;

public interface SurveyResultDetailResponse {
    Integer getSurveyResultId();

    Integer getUserId();

    String getUserName();

    String getEmail();

    Integer getEvaluatorId();

    String getEvaluatorName();

    Integer getSurveyId();

    String getTitle();

    String getDescription();

    String getDeadline();

    Integer getCourseId();

    String getCourseName();

    Integer getQuestionId();

    String getQuestionText();

    String getType();

    Integer getAnswerId();

    String getContent();

    Integer getPoint();
}
