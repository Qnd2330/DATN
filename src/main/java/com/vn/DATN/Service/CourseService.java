package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.CourseDTO;
import com.vn.DATN.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CourseService {

    Page<Course> listCourse(Pageable pageable);

    List<Course> getAll();

    Course create(CourseDTO CourseDTO);

    Course edit(CourseDTO CourseDTO);

    Course findByCourseName(String courseName);

    Course findById(Integer CourseId);

    boolean delete(Integer CourseId);
}
