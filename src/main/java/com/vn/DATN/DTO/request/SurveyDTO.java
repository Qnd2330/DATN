package com.vn.DATN.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDTO {
    private Integer surveyId;
    private String title;
    private String description;
    private Integer score;
    private boolean haveCourse;
    private String courseName;
    private List<QuestionDTO> questionDTO;
}
