package com.vn.DATN.DTO.response;

public interface StatisticsOverviewProjection {
    Long getTotalSurveys();
    Long getTotalStudents();
    Long getCompletedSurveys();
    Double getCompletionRate();
}