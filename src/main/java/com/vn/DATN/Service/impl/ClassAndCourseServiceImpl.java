package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.ClassAndCourseDTO;
import com.vn.DATN.Service.ClassAndCourseService;
import com.vn.DATN.Service.ClassService;
import com.vn.DATN.Service.CourseService;
import com.vn.DATN.Service.repositories.ClassAndCourseRepo;
import com.vn.DATN.entity.Class;
import com.vn.DATN.entity.ClassCourse;
import com.vn.DATN.entity.Course;
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
    public List<ClassCourse> linkClassWithCourse(ClassAndCourseDTO dto) {
        Class clazz = getClass(dto.getClassId()); // lấy class 1 lần
        List<Course> courses = dto.getCourseId().stream()
                .map(this::getCourse)
                .collect(Collectors.toList());

        List<ClassCourse> classCourses = courses.stream()
                .map(course -> ClassCourse.builder()
                        .classes(clazz)
                        .course(course)
                        .build())
                .collect(Collectors.toList());

        return classAndCourseRepo.saveAll(classCourses);
    }

    @Override
    public List<ClassCourse> editLinkClassWithCourse(ClassAndCourseDTO dto) {
        Class clazz = getClass(dto.getClassId());
        // 1. Lấy các liên kết hiện tại từ DB
        List<ClassCourse> existingLinks = classAndCourseRepo.findByClasses(clazz);
        Set<Integer> existingCourseIds = existingLinks.stream()
                .map(cc -> cc.getCourse().getCourseId())
                .collect(Collectors.toSet());

        Set<Integer> newCourseIds = new HashSet<>(dto.getCourseId());

        // 2. Xác định các liên kết cần xóa (có trong DB nhưng không còn trong DTO)
        List<ClassCourse> toDelete = existingLinks.stream()
                .filter(cc -> !newCourseIds.contains(cc.getCourse().getCourseId()))
                .toList();

        // 3. Xác định các liên kết cần thêm (có trong DTO nhưng không có trong DB)
        List<ClassCourse> toAdd = newCourseIds.stream()
                .filter(id -> !existingCourseIds.contains(id))
                .map(id -> ClassCourse.builder()
                        .classes(clazz)
                        .course(getCourse(id))
                        .build())
                .toList();

        // 4. Thực hiện cập nhật
        classAndCourseRepo.deleteAll(toDelete);
        List<ClassCourse> addedLinks = classAndCourseRepo.saveAll(toAdd);

        // 5. Trả về toàn bộ liên kết hiện tại sau cập nhật
        List<ClassCourse> updatedLinks = classAndCourseRepo.findByClasses(clazz);
        return updatedLinks;
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
