package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Class;
import com.vn.DATN.entity.ClassCourse;
import com.vn.DATN.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassAndCourseRepo extends JpaRepository<ClassCourse, Integer> {
    ClassCourse findByClassesAndCourse(Class classes, Course course);
}
