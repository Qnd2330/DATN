package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.CourseSurveyDTO;
import com.vn.DATN.entity.Course;
import com.vn.DATN.entity.CourseSurvey;
import com.vn.DATN.entity.Survey;

public interface CourseAndSurveyService {
    CourseSurvey linkSurveyToCourse (Course course, Survey survey);
}
