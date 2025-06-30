package com.vn.DATN.DTO.mapper;

import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.DTO.request.AnswerDTO;
import com.vn.DATN.entity.Question;
import com.vn.DATN.entity.QuestionAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionAnswerMapper {
    QuestionAnswerMapper INSTANCE = Mappers.getMapper(QuestionAnswerMapper.class);

    default List<QuestionDTO> toQuestionDTOList(List<QuestionAnswer> questionAnswers) {
        if (questionAnswers == null || questionAnswers.isEmpty()) return Collections.emptyList();

        // Group theo questionId
        Map<Integer, List<QuestionAnswer>> grouped = questionAnswers.stream()
                .collect(Collectors.groupingBy(qa -> qa.getQuestion().getQuestionId()));

        // Convert mỗi nhóm thành 1 QuestionDTO
        List<QuestionDTO> dtoList = new ArrayList<>();
        for (List<QuestionAnswer> group : grouped.values()) {
            dtoList.add(toQuestionDTOFromGroup(group));
        }
        return dtoList;
    }

    // Map từng nhóm QuestionAnswer (cùng questionId) thành 1 QuestionDTO
    default QuestionDTO toQuestionDTOFromGroup(List<QuestionAnswer> group) {
        if (group == null || group.isEmpty()) return null;

        Question question = group.get(0).getQuestion();
        QuestionDTO dto = new QuestionDTO();
        dto.setQuestionId(question.getQuestionId());
        dto.setQuestionText(question.getQuestionText());
        dto.setType(question.getType());

        List<AnswerDTO> answers = group.stream()
                .map(qa -> AnswerMapper.INSTANCE.toDTO(qa.getAnswer()))
                .collect(Collectors.toList());

        dto.setAnswers(answers);
        return dto;
    }

}
