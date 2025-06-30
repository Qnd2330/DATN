package com.vn.DATN.DTO.response;

import com.vn.DATN.entity.Survey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyResponse {
    private Integer surveyId;
    private String title;
    private String description;
    private String courseName;

    public static SurveyResponse fromSurvey(Survey survey){
        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.setSurveyId(survey.getSurveyId());
        surveyResponse.setTitle(survey.getTitle());
        surveyResponse.setDescription(survey.getDescription());
        return surveyResponse;
    }
}
