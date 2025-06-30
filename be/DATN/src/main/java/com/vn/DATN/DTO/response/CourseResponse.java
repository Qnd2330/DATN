package com.vn.DATN.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vn.DATN.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    private Integer courseId;
    private String courseName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;
    private String teacherName;

    public static CourseResponse fromCourse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setCourseId(course.getCourseId());
        response.setCourseName(course.getCourseName());
        response.setStartDate(course.getStartDate());
        response.setEndDate(course.getEndDate());
        if (course.getUsers() != null) {
            response.setTeacherName(course.getUsers().getUserNamee());
        } else {
            response.setTeacherName(null);
        }
        return response;
    }
}