package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.DTO.request.SurveyQuestionDTO;
import com.vn.DATN.entity.SurveyQuestion;

public interface SurveyAndQuestionService {
    SurveyDTO create(SurveyDTO SurveyDTO);

    SurveyQuestion edit(SurveyDTO SurveyDTO);

    SurveyQuestion findById(Integer SurveyQuestionId);

    boolean existsById(Integer SurveyQuestionId);

    boolean delete(Integer SurveyQuestionId);
}
