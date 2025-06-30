package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.ClassAndCourseDTO;
import com.vn.DATN.Service.ClassAndCourseService;
import com.vn.DATN.Service.ClassService;
import com.vn.DATN.Service.CourseService;
import com.vn.DATN.Service.repositories.ClassAndCourseRepo;
import com.vn.DATN.entity.Class;
import com.vn.DATN.entity.ClassCourse;
import com.vn.DATN.entity.Course;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassAndCourseServiceImpl implements ClassAndCourseService {
    private final ClassAndCourseRepo classAndCourseRepo;

    private final ClassService classService;

    private final CourseService courseService;

    @Override
    @Transactional
    public List<ClassCourse> linkClassWithCourse(ClassAndCourseDTO dto) {
        Class clazz = getClass(dto.getClassId());
        
        List<ClassCourse> existingLinks = classAndCourseRepo.findByClasses(clazz);
        Set<Integer> existingCourseIds = existingLinks.stream()
                .map(cc -> cc.getCourse().getCourseId())
                .collect(Collectors.toSet());

        Set<Integer> newCourseIds = new HashSet<>(dto.getCourseId());
        
        List<ClassCourse> toDelete = existingLinks.stream()
                .filter(cc -> !newCourseIds.contains(cc.getCourse().getCourseId()))
                .toList();
        
        List<ClassCourse> toAdd = newCourseIds.stream()
                .filter(id -> !existingCourseIds.contains(id))
                .map(id -> ClassCourse.builder()
                        .classes(clazz)
                        .course(getCourse(id))
                        .build())
                .toList();
        
        classAndCourseRepo.deleteAll(toDelete);
        classAndCourseRepo.saveAll(toAdd);

        return classAndCourseRepo.findByClasses(clazz);
    }

    private Course getCourse(Integer courseId) {
        Course course = courseService.findById(courseId);
        if(course == null){
            throw new RuntimeException("Khóa học này không tồn tại");
        }
        return course;
    }

    private Class getClass(Integer classId) {
        Class thisClass = classService.findById(classId);
        if(thisClass == null ){
            throw new RuntimeException("Lớp này không tồn tại");
        }
        return thisClass;
    }

    @Override
    @Transactional
    public void deleteLinkClassWithCourse(ClassAndCourseDTO classAndCourseDTO) {
            Class aClass = getClass(classAndCourseDTO.getClassId());
            Course course = getCourse(classAndCourseDTO.getCourseId().get(0));
            ClassCourse classCourse = classAndCourseRepo.findByClassesAndCourse(aClass, course);
            classAndCourseRepo.delete(classCourse);
    }
}
