package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.entity.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SurveyService {
    Page<Survey> getAll (Pageable pageable);

    Survey create(SurveyDTO surveyDTO);

    Survey edit(SurveyDTO surveyDTO);

    Survey findById(Integer surveyId);
    SurveyDTO getById(Integer surveyId);

    boolean existsById(Integer surveyId);

    boolean delete(Integer surveyId);
}
