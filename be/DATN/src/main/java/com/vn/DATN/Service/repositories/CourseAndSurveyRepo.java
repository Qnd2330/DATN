package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Course;
import com.vn.DATN.entity.CourseSurvey;
import com.vn.DATN.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseAndSurveyRepo extends JpaRepository<CourseSurvey, Integer> {
    Optional<CourseSurvey> findByCourseAndSurvey(Course course, Survey survey);
    Optional<CourseSurvey> findBySurvey(Survey survey);
}
