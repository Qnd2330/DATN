package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.entity.Survey;

public interface SurveyService {

    Survey create(SurveyDTO surveyDTO);

    Survey edit(SurveyDTO surveyDTO);

    Survey findById(Integer surveyId);

    boolean existsById(Integer surveyId);

    boolean delete(Integer surveyId);
}
