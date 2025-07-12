package com.vn.DATN.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyStatisticsDTO {
    private String facultyName;
    private Long totalStudents;
    private Long totalSurveys;
    private Long completedSurveys;
    private Double completionRate;
} 