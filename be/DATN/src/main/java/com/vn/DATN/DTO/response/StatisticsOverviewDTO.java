package com.vn.DATN.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsOverviewDTO {
    private Long totalSurveys;
    private Long totalStudents;
    private Long completedSurveys;
    private Double completionRate;
} 