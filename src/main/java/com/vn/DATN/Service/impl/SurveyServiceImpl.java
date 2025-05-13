package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.Service.CourseService;
import com.vn.DATN.Service.SurveyService;
import com.vn.DATN.Service.repositories.SurveyRepo;
import com.vn.DATN.entity.Course;
import com.vn.DATN.entity.Survey;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepo surveyRepo;

    private final CourseService courseService;

    @Override
    @Transactional
    public Survey create(SurveyDTO request) {
        Survey survey;
        Course course = courseService.findByCourseName(request.getCourseName());
        if(course == null) {
            throw new RuntimeException("Không tìm thấy môn học");
        }
        survey = Survey.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .score(request.getScore())
                .course(course)
                .build();
        return surveyRepo.save(survey);
    }

    @Override
    public Survey edit(SurveyDTO request) {
        Survey survey = findById(request.getSurveyId());
        if (survey == null) {
            throw new RuntimeException("Không tìm thấy phiếu đánh giá " + request.getTitle());
        }
        Course course = courseService.findByCourseName(request.getCourseName());
        if(course == null) {
            throw new RuntimeException("Không tìm thấy môn học");
        }
        survey.setTitle(request.getTitle());
        survey.setDescription(request.getDescription());
        survey.setScore(request.getScore());
        survey.setCourse(course);
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
