package com.vn.DATN.DTO.mapper;
import com.vn.DATN.DTO.request.AnswerDTO;
import com.vn.DATN.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    AnswerDTO toDTO(Answer answer);
    Answer toEntity(AnswerDTO answerDTO);
}