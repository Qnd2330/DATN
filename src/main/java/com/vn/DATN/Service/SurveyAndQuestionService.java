package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.DTO.request.SurveyQuestionDTO;
import com.vn.DATN.entity.SurveyQuestion;

public interface SurveyAndQuestionService {
    SurveyDTO create(SurveyDTO SurveyDTO);
    boolean delete(Integer SurveyQuestionId);
}
