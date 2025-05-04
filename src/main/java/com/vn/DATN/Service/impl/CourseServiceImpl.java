package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.CourseDTO;
import com.vn.DATN.Service.CourseService;
import com.vn.DATN.Service.UsersService;
import com.vn.DATN.Service.repositories.CourseRepo;
import com.vn.DATN.entity.Course;
import com.vn.DATN.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;

    private final UsersService usersService;

    @Override
    public Course create(CourseDTO courseDTO) {
        Course course;
        Users users = usersService.findByUserName(courseDTO.getUsersName());
        if(users == null){
            throw new RuntimeException("Không tìm thấy người dùng");
        }
        course = Course.builder()
                .courseName(courseDTO.getCourseName())
                .duration(courseDTO.getDuration())
                .users(users)
                .build();
        return courseRepo.save(course);
    }

    @Override
    public Course edit(CourseDTO courseDTO) {
        Course course = findById(courseDTO.getCourseId());
        if(course == null){
            throw new RuntimeException("Không tìm thấy khóa học");
        }
        Users users = usersService.findByUserName(courseDTO.getUsersName());
        if(users == null){
            throw new RuntimeException("Không tìm thấy người dùng");
        }
        course.setCourseName(courseDTO.getCourseName());
        course.setDuration(courseDTO.getDuration());
        course.setUsers(users);
        return courseRepo.saveAndFlush(course);
    }

    @Override
    public Course findByCourseName(String courseName) {
        Course course = courseRepo.findByCourseName(courseName);
        if(course == null){
            return null;
        }
        return course;
    }

    @Override
    public Course findById(Integer courseId) {
        return courseRepo.findById(courseId).orElse(null);
    }

    @Override
    public boolean deleteById(Integer courseId) {
        if (!courseRepo.existsById(courseId)) {
            return false;
        }
        courseRepo.deleteById(courseId);
        return true;
    }
}
