package com.vn.DATN.Service;

import com.vn.DATN.entity.Course;
import com.vn.DATN.entity.CourseSurvey;
import com.vn.DATN.entity.Survey;

import java.util.Optional;

public interface CourseAndSurveyService {
    CourseSurvey linkSurveyToCourse (Course course, Survey survey);
    Optional<CourseSurvey> findBySurvey(Survey survey);
}
