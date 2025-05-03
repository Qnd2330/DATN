//package com.vn.DATN.DTO.mapper;
//
//import com.vn.DATN.DTO.request.SurveyDTO;
//import com.vn.DATN.entity.Survey;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.processing.Generated;
//
//@Generated(
//        value = "org.mapstruct.ap.MappingProcessor",
//        date = "2025-05-02T20:45:32+0700",
//        comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Amazon.com Inc.)"
//)
//@Component
//public class SurveyMapperImpl implements SurveyMapper{
//    @Override
//    public SurveyDTO toDTO(Survey survey) {
//        if ( survey == null ) {
//            return null;
//        }
//
//        SurveyDTO surveyDTO = new SurveyDTO();
//
//        surveyDTO.setSurveyId(survey.getSurveyId());
//        surveyDTO.setTitle(survey.getTitle());
//        surveyDTO.setDescription(survey.getDescription());
//        surveyDTO.setScore(survey.getScore());
//        surveyDTO.setCourse(survey.getCourse());
//
//        return surveyDTO;
//    }
//
//    @Override
//    public Survey toEntity(SurveyDTO surveyDTO) {
//        if ( surveyDTO == null ) {
//            return null;
//        }
//
//        Survey.SurveyBuilder survey = Survey.builder();
//
//        survey.surveyId(surveyDTO.getSurveyId());
//        survey.title(surveyDTO.getTitle());
//        survey.description(surveyDTO.getDescription());
//        survey.score(surveyDTO.getScore());
//        survey.course(surveyDTO.getCourse());
//
//        return survey.build();
//    }
//}
