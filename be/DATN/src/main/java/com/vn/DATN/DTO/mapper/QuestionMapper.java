package com.vn.DATN.DTO.mapper;

import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    QuestionDTO toDTO(Question question);

    Question toEntity(QuestionDTO dto);

    List<QuestionDTO> toDTOList(List<Question> questionList);

    List<Question> toEntityList(List<QuestionDTO> dtoList);
}
