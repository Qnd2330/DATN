package com.vn.DATN.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vn.DATN.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    private Integer courseId;
    private String courseName;
    private String teacherName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime updatedAt;

    public static CourseResponse fromCourse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setCourseId(course.getCourseId());
        response.setCourseName(course.getCourseName());
        response.setCreateAt(course.getCreateAt());
        response.setUpdatedAt(course.getUpdatedAt());
        if (course.getUsers() != null) {
            response.setTeacherName(course.getUsers().getUserNamee());
        } else {
            response.setTeacherName(null);
        }
        return response;
    }
}