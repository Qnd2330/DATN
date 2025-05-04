package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Course;
import com.vn.DATN.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, Integer> {
    Course findByCourseName (String courseName);
}
