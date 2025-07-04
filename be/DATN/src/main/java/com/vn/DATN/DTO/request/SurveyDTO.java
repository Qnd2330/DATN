package com.vn.DATN.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDTO {
    private Integer surveyId;
    private String title;
    private String description;
    private boolean haveCourse;
    private String courseName;
    private LocalDate deadline;
    private List<QuestionDTO> questionDTO;
}
