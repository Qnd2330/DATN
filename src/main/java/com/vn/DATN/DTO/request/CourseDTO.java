package com.vn.DATN.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Integer courseId;
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String teacherName;
}
