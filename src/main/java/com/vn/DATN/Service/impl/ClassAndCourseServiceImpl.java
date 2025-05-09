package com.vn.DATN.Service.impl;

import com.vn.DATN.Service.ClassAndCourseService;
import com.vn.DATN.Service.ClassService;
import com.vn.DATN.Service.CourseService;
import com.vn.DATN.Service.repositories.ClassAndCourseRepo;
import com.vn.DATN.entity.Class;
import com.vn.DATN.entity.ClassCourse;
import com.vn.DATN.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassAndCourseServiceImpl implements ClassAndCourseService {
    private final ClassAndCourseRepo classAndCourseRepo;

    private final ClassService classService;

    private final CourseService courseService;

    @Override
    public ClassCourse linkClassWithCourse(Integer classId, Integer courseId) {
        ClassCourse classCourse = ClassCourse.builder()
                .classes(getClass(classId))
                .course(getCourse(courseId))
                .build();
        return classAndCourseRepo.save(classCourse);
    }

    @Override
    public ClassCourse editLinkClassWithCourse(Integer classId, Integer courseId, Integer newCourseId) {
        ClassCourse classCourse = classAndCourseRepo.findByClassesAndCourse(getClass(classId),getCourse(courseId));
        if (classCourse != null) {
            classAndCourseRepo.delete(classCourse);

            Class classes = getClass(classId);
            Course newCourse = getCourse(newCourseId);

            ClassCourse newClassCourse = ClassCourse.builder()
                    .classes(classes)
                    .course(newCourse)
                    .build();

           return classAndCourseRepo.save(newClassCourse);
        }
        return classCourse;
    }

    private Course getCourse(Integer courseId) {
        Course course = courseService.findById(courseId);
        if(course == null){
            throw new RuntimeException("Khóa học này không tồn tại");
        }
        return course;
    }

    private Class getClass(Integer classId) {
        Class classs = classService.findById(classId);
        if(classs == null ){
            throw new RuntimeException("Lớp này không tồn tại");
        }
        return classs;
    }

    @Override
    public ClassCourse deleteLinkClassWithCourse(Integer classId) {
        return null;
    }
}
