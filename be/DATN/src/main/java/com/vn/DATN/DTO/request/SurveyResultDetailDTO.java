package com.vn.DATN.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResultDetailDTO {
    private Integer surveyResultId;
    private Integer userId;
    private String userName;
    private String email;

    private Integer evaluatorId;
    private String evaluatorName;

    private Integer surveyId;
    private String title;
    private String description;
    private String deadline;

    private Integer courseId;
    private String courseName;

    private List<QuestionDTO> questions = new ArrayList<>();
}
