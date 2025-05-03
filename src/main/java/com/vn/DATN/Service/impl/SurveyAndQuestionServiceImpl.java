package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.mapper.QuestionMapper;
import com.vn.DATN.DTO.mapper.SurveyQuestionMapper;
import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.Service.QuestionAndAnswerService;
import com.vn.DATN.Service.SurveyAndQuestionService;
import com.vn.DATN.Service.SurveyService;
import com.vn.DATN.Service.repositories.SurveyAndQuestionRepo;
import com.vn.DATN.entity.Question;
import com.vn.DATN.entity.Survey;
import com.vn.DATN.entity.SurveyQuestion;
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

        List<QuestionDTO> questionsDTO = questionAndAnswerService.create(surveyDTO.getQuestionDTO());

        Survey survey = surveyService.create(surveyDTO);

        List<Question> questions = QuestionMapper.INSTANCE.toEntityList(questionsDTO);
            for (Question question : questions) {
                surveyQuestions.add(SurveyQuestion.builder()
                        .survey(survey)
                        .question(question)
                        .build());
            }

        List<SurveyQuestion> surveyQuestionList = surveyAndQuestionRepo.saveAll(surveyQuestions);
        return SurveyQuestionMapper.INSTANCE.toSurveyDTO(surveyQuestions,questionsDTO);
    }

    @Override
    public SurveyQuestion edit(SurveyDTO SurveyQuestionDTO) {
        return null;
    }

    @Override
    public SurveyQuestion findById(Integer SurveyQuestionId) {
        return null;
    }

    @Override
    public boolean existsById(Integer SurveyQuestionId) {
        return false;
    }

    @Override
    public boolean delete(Integer SurveyQuestionId) {
        return false;
    }
}
