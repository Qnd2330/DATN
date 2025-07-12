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
import java.util.Set;
import java.util.stream.Collectors;

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
        Survey survey = surveyService.edit(surveyDTO);
        List<SurveyQuestion> existingSurveyQuestions = surveyAndQuestionRepo.findBySurvey(survey);
        Set<Integer> existingQuestionAnswerIds = existingSurveyQuestions.stream()
                .map(sq -> sq.getQuestionAnswer().getId())
                .collect(Collectors.toSet());
        List<SurveyQuestion> surveyQuestions = new ArrayList<>();
        List<QuestionAnswer> questionsDTO = questionAndAnswerService.update(surveyDTO.getQuestionDTO());

        for (QuestionAnswer question : questionsDTO) {
            if (!existingQuestionAnswerIds.contains(question.getId())) {
                surveyQuestions.add(SurveyQuestion.builder()
                        .survey(survey)
                        .questionAnswer(question)
                        .build());
            }
        }

        // Chỉ save khi có SurveyQuestion mới
        if (!surveyQuestions.isEmpty()) {
            surveyAndQuestionRepo.saveAll(surveyQuestions);
        }


        return surveyDTO;
    }

    @Override
    public boolean delete(Integer surveyId) {
        return true;
    }
}
