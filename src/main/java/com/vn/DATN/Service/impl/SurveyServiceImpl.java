package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.Service.SurveyService;
import com.vn.DATN.Service.repositories.SurveyRepo;
import com.vn.DATN.entity.Survey;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepo surveyRepo;

    @Override
    @Transactional
    public Survey create(SurveyDTO request) {
        Survey survey = surveyRepo.findSurveyByTitle(request.getTitle());
        if (survey != null) {
            return survey;
        }
        survey = Survey.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .score(request.getScore())
                .course(request.getCourse())
                .build();
        return surveyRepo.save(survey);
    }

    @Override
    public Survey edit(SurveyDTO request) {
        Survey survey = surveyRepo.findSurveyByTitle(request.getTitle());
        if (survey == null) {
            throw new RuntimeException("Không tìm thấy phiếu đánh giá " + request.getTitle());
        }
        survey.setTitle(request.getTitle());
        survey.setDescription(request.getDescription());
        survey.setScore(request.getScore());
        survey.setCourse(request.getCourse());
        return surveyRepo.saveAndFlush(survey);
    }

    @Override
    public Survey findById(Integer surveyId) {
        return surveyRepo.findById(surveyId).orElse(null);
    }

    @Override
    public boolean existsById(Integer surveyId) {
        if (surveyId == null) {
            return false;
        }
        return surveyRepo.existsById(surveyId);
    }

    @Override
    public boolean delete(Integer surveyId) {
        if (!surveyRepo.existsById(surveyId)) {
            return false;
        }
        surveyRepo.deleteById(surveyId);
        return true;
    }
}
