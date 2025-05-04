package com.vn.DATN.DTO.mapper;

import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.entity.Question;
import com.vn.DATN.entity.Survey;
import com.vn.DATN.entity.SurveyQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SurveyQuestionMapper {

    SurveyQuestionMapper INSTANCE = Mappers.getMapper(SurveyQuestionMapper.class);

    default SurveyDTO toSurveyDTO(List<SurveyQuestion> surveyQuestions, List<QuestionDTO> questionDTOS) {
        if (surveyQuestions == null || surveyQuestions.isEmpty()) return null;
        Survey survey = surveyQuestions.get(0).getSurvey();
        SurveyDTO dto = new SurveyDTO();
        dto.setSurveyId(survey.getSurveyId());
        dto.setTitle(survey.getTitle());
        dto.setDescription(survey.getDescription());
        dto.setScore(survey.getScore());
        dto.setCourseName(survey.getCourse().getCourseName());

        Map<Integer, List<SurveyQuestion>> grouped = surveyQuestions.stream()
                .collect(Collectors.groupingBy(q -> q.getQuestion().getQuestionId()));

        List<QuestionDTO> questions = new ArrayList<>();
        for (List<SurveyQuestion> group : grouped.values()) {
            questions.add(toQuestionDTOFromGroup(group,questionDTOS));
        }
        dto.setQuestionDTO(questions);
        return dto;
    }

    default QuestionDTO toQuestionDTOFromGroup(List<SurveyQuestion> group, List<QuestionDTO> questionDTOS) {
        if (group == null || group.isEmpty()) return null;

        Question question = group.get(0).getQuestion();
        // Tìm QuestionDTO tương ứng trong list ban đầu truyền vào
        QuestionDTO dto = questionDTOS.stream()
                .filter(q -> q.getQuestionId().equals(question.getQuestionId()))
                .findFirst()
                .orElse(new QuestionDTO()); // hoặc null tùy bạn muốn

        dto.setQuestionId(question.getQuestionId());
        dto.setQuestionText(question.getQuestionText());
        dto.setType(question.getType());

        return dto;
    }
}
