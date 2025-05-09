package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.CourseDTO;
import com.vn.DATN.entity.Course;

public interface CourseService {

    Course create(CourseDTO CourseDTO);

    Course edit(CourseDTO CourseDTO);

    Course findByCourseName(String courseName);

    Course findById(Integer CourseId);

    boolean delete(Integer CourseId);
}
