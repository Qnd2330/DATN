package com.vn.DATN.DTO.mapper;

import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.entity.Course;
import com.vn.DATN.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SurveyMapper {
    SurveyMapper INSTANCE = Mappers.getMapper(SurveyMapper.class);
    SurveyDTO toDTO(Survey survey);
    Survey toEntity(SurveyDTO surveyDTO, Course course);
}
