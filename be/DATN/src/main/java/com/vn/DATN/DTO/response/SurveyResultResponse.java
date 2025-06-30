package com.vn.DATN.DTO.response;

public interface SurveyResultResponse {
    String getSurveyResultId();

    String getSurveyId();
    String getSrStudentId();

    String getUserId();

    String getStudentName();     // u_target.user_name AS student_name

    String getEvaluatorId();

    String getEvaluatorName();

    String getSurveyTitle();     // s.title AS survey_title

    String getCreateAt(); // sr.create_at

    boolean getIsMonitor();
}

