package com.vn.DATN.DTO.response;

public interface ClassStatisticsProjection {
    String getClassName();
    Long getTotalStudents();
    Long getTotalSurveys();
    Long getCompletedSurveys();
    Double getCompletionRate();
}