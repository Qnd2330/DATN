package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.Service.QuestionAndAnswerService;
import com.vn.DATN.Service.SurveyAndQuestionService;
import com.vn.DATN.Service.SurveyService;
import com.vn.DATN.Service.repositories.SurveyAndQuestionRepo;
import com.vn.DATN.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyAndQuestionServiceImpl implements SurveyAndQuestionService {
    private final SurveyAndQuestionRepo surveyAndQuestionRepo;
    private final SurveyService surveyService;
    private final QuestionAndAnswerService questionAndAnswerService;

    @Override
    public SurveyDTO create(SurveyDTO surveyDTO) {
        List<SurveyQuestion> surveyQuestions = new ArrayList<>();

        List<QuestionAnswer> questionsDTO = questionAndAnswerService.create(surveyDTO.getQuestionDTO());

        Survey survey = surveyService.create(surveyDTO);

            for (QuestionAnswer question : questionsDTO) {
                surveyQuestions.add(SurveyQuestion.builder()
                        .survey(survey)
                        .questionAnswer(question)
                        .build());
            }
            surveyAndQuestionRepo.saveAll(surveyQuestions);
        return surveyDTO;
    }

    public SurveyDTO update (SurveyDTO surveyDTO) {
        surveyService.edit(surveyDTO);
        questionAndAnswerService.update(surveyDTO.getQuestionDTO());
        return surveyDTO;
    }

    @Override
    public boolean delete(Integer surveyId) {
        return true;
    }
}
