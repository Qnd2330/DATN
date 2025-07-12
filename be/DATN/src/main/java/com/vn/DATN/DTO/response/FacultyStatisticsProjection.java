package com.vn.DATN.DTO.response;

public interface FacultyStatisticsProjection {
    String getFacultyName();
    Long getTotalStudents();
    Long getTotalSurveys();
    Long getCompletedSurveys();
    Double getCompletionRate();
}