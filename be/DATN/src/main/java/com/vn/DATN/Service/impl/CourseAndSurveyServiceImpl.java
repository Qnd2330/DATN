package com.vn.DATN.Service.impl;

import com.vn.DATN.Service.CourseAndSurveyService;
import com.vn.DATN.Service.repositories.CourseAndSurveyRepo;
import com.vn.DATN.entity.Course;
import com.vn.DATN.entity.CourseSurvey;
import com.vn.DATN.entity.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseAndSurveyServiceImpl implements CourseAndSurveyService {
    private final CourseAndSurveyRepo courseAndSurveyRepo;
    @Override
    public CourseSurvey linkSurveyToCourse(Course course, Survey survey) {
        Optional<CourseSurvey> optional = courseAndSurveyRepo.findByCourseAndSurvey(course, survey);

        CourseSurvey courseSurvey;

        if (optional.isPresent()) {
            courseSurvey = optional.get();
            courseSurvey.setCourse(course);
            courseSurvey.setSurvey(survey);
        } else {
            courseSurvey = CourseSurvey.builder()
                    .course(course)
                    .survey(survey)
                    .build();
        }

        return courseAndSurveyRepo.save(courseSurvey);
    }

    @Override
    public Optional<CourseSurvey> findBySurvey(Survey survey) {
        return courseAndSurveyRepo.findBySurvey(survey);
    }
}
