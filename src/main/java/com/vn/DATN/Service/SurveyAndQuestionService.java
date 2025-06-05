package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.SurveyDTO;

public interface SurveyAndQuestionService {
    SurveyDTO create(SurveyDTO SurveyDTO);
    SurveyDTO update (SurveyDTO surveyDTO);
    boolean delete(Integer SurveyQuestionId);
}
