package com.vn.DATN.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {
    private Integer userId;
    private Integer surveyId;
    private Integer score;
    private List<UserAnswerDTO> userAnswers;
}
